package com.ewcms.personal.memoranda.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.jpa.repository.Query;


import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personal.memoranda.entity.Memoranda;

/**
 * @author 吴智俊
 */
public interface MemorandaRepository extends BaseRepository<Memoranda, Long> {
	
//	@Query("from Calendar m where m.noteDate>=?1 and m.noteDate<?2 and m.userName=?3 order by m.warnTime desc, m.noteDate desc, m.id desc")
//	List<Calendar> findMemorandaByDate(Date beginDate, Date endDate, String userName);
	List<Memoranda> findByNoteDateBetweenAndNoteDateBeforeAndUserIdOrderByWarnTimeDescIdDesc(Date beginDate, Date endDate, Date endDate1, Long userId);
	
//	@Query("select m from Calendar m where m.userName=?1 and m.warn=true and m.warnTime Is not null and m.noteDate is not null")
//	List<Calendar> findMemorandaByWarn(String userName);
	List<Memoranda> findByUserIdAndWarnTrueAndWarnTimeIsNotNullAndNoteDateIsNotNull(Long userId);
	
//	@Query("select m from Calendar m where m.userName=?1 order by m.noteDate desc, m.warnTime desc, m.id desc")
//	List<Calendar> findMemorandaByUserName(String userName);
	List<Memoranda> findByUserIdOrderByNoteDateDescWarnTimeDescIdDesc(Long userId);
}
