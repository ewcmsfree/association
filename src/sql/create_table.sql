ALTER TABLE sec_user_last_online ALTER COLUMN id SET DEFAULT nextval('seq_sec_user_last_online_id'::regclass);


/**
 * DROP TRIGGER trigger_sec_user_off_online ON sec_user_online;
 * DROP FUNCTION fun_user_off_online();
 */
create or replace function fun_user_off_online() returns trigger AS
$BODY$
begin
   if OLD.user_id is not null then
      if not exists(select user_id from sec_user_last_online where user_id = OLD.user_id) then
        insert into sec_user_last_online
                  (user_id, uid, host, user_agent, system_host,
                   last_login_timestamp, last_stop_timestamp, login_count, total_online_time)
                values
                   (OLD.user_id, OLD.id, OLD.host, OLD.user_agent, OLD.system_host,
                    OLD.start_timestamp, OLD.last_access_time,
                    1, extract('epoch' from (OLD.last_access_time - OLD.start_timestamp)));
      else
        update sec_user_last_online
          set uid = OLD.id, host = OLD.host, user_agent = OLD.user_agent,
            system_host = OLD.system_host, last_login_timestamp = OLD.start_timestamp,
             last_stop_timestamp = OLD.last_access_time, login_count = login_count + 1,
             total_online_time = total_online_time + extract('epoch' from (OLD.last_access_time - OLD.start_timestamp))
        where user_id = OLD.user_id;
      end if ;
   end if;
   return null;
end;
$BODY$
LANGUAGE plpgsql;
create trigger trigger_sec_user_off_online after delete on sec_user_online for each row execute procedure fun_user_off_online();