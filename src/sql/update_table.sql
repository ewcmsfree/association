ALTER TABLE sys_report_data_static DROP COLUMN value;
ALTER TABLE sys_report_data_static ADD COLUMN value text;

ALTER TABLE sys_report_parameter DROP COLUMN value;
ALTER TABLE sys_report_parameter ADD COLUMN value text;