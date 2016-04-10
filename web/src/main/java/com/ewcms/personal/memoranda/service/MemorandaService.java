package com.ewcms.personal.memoranda.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personal.memoranda.entity.Memoranda;
import com.ewcms.personal.memoranda.entity.Memoranda.BeforeStatus;
import com.ewcms.personal.memoranda.entity.Memoranda.FrequencyStatus;
import com.ewcms.personal.memoranda.repository.MemorandaRepository;
import com.ewcms.personal.memoranda.util.Lunar;
import com.ewcms.personal.memoranda.util.SolarTerm;

/**
 * 
 * @author wu_zhijun
 */
@Service
public class MemorandaService extends BaseService<Memoranda, Long>{

	protected static final Logger logger = LoggerFactory.getLogger(MemorandaService.class);
	
	private final static String POSITION_PREV="prev_";
	private final static String POSITION_NEXT="next_";
	
	private MemorandaRepository getMemorandaRepository(){
		return (MemorandaRepository) baseRepository;
	}
	
	private int dayCount;
	private int currentMonth;
	private int currentDay;
	private int selYear;
	private int selMonth;
	private int prevMonth;
	private int nextMonth;
	
	public StringBuffer getInitCalendarToHtml(Long userId, int year, int month) {
		StringBuffer sb = new StringBuffer();
		
		Calendar calendar = Calendar.getInstance();
		
		int currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		
		if (currentYear == year && currentMonth == month - 1){
			currentDay = calendar.get(Calendar.DATE);
		}else{
			currentDay = 0;
		}
		
		calendar.set(Calendar.YEAR, year); 
		calendar.set(Calendar.MONTH, month - 1);
		
		selYear = year;
		selMonth = month;
		prevMonth = month - 1;
		nextMonth = month + 1;
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int week = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		dayCount = 1;
		
		sb.append(getTitleHtml().toString());
		sb.append(generatorFirstWeekHtml(userId, firstDayOfMonth).toString());
		sb.append(generatorMiddleWeekHtml(userId, week).toString());
		sb.append(generatorLastWeekHtml(userId, days,firstDayOfMonth).toString());
//		sb.append(generatorJs().toString());
		
		return sb;
	}
	
//	private StringBuffer generatorJs(){
//		StringBuffer sb = new StringBuffer();
//		sb.append("<script type='text/javascript'>\n");
//		sb.append("    $('.a_memoranda_value').draggable({revert:true,proxy:'clone'});\n");
//        sb.append("    $('.div_memoranda').droppable({\n");
//        sb.append("     onDragEnter:function(e, source){\n");
//        //sb.append("       $(this).addClass('over');\n");
//        sb.append("     },\n");
//        sb.append("     onDragLeave:function(e, source){\n");
//        //sb.append("       $(this).removeClass('over');\n");
//        sb.append("     },\n");
//        sb.append("     onDrop:function(e, source){\n");
//        //sb.append("       $(this).removeClass('over');\n");
//        sb.append("       $(this).append(source);\n");
//        sb.append("       var divMemoId = $(source).attr('id');\n");    
//        sb.append("       var memoId = divMemoId.split('_')[3];\n");
//        sb.append("       var targetDivId = $(source).parents('div:first').attr('id');\n");
//        sb.append("       var dropDays = targetDivId.split('_');\n");
//        sb.append("       var dropDay;\n");
//        sb.append("       var month=$('#month').val();\n");
//        sb.append("       if (dropDays[2]=='prev'){\n");
//        sb.append("         month = parseInt(month) - 1;\n");
//        sb.append("         dropDay = dropDays[3];\n");
//        sb.append("       }else if (dropDays[2]=='next'){\n");
//        sb.append("         month = parseInt(month) + 1;\n");
//        sb.append("         dropDay = dropDays[3];\n");
//        sb.append("       }else{\n");
//        sb.append("         dropDay = dropDays[2];\n");
//        sb.append("       }\n");
//        sb.append("       $.post('${ctx}/personal/memoranda/'  + memorandaId + '/' + $('#year').val() + '/' + $('#month').val() + '/' + dropDay + '/drop',{},function(data){\n");
//        sb.append("         if (!data){\n");
//        sb.append("         }\n");
//        sb.append("       });\n");
//        sb.append("     }\n");
//        sb.append("    });\n");
//        sb.append("</script>\n");
//          
//        return sb;
//	}
	
	private StringBuffer generatorContentHtml(Long userId, int year, int month, int day, String position){
		StringBuffer sb = new StringBuffer();
		
		String lunarValue= getLunarDay(year, month, day);
		if (!getSolarTerms(year, month, day).equals("")) lunarValue = getSolarTerms(year, month, day);
		
		List<Memoranda> memos = findMemorandaByDate(userId, year, month, day);
		StringBuffer memoSb = new StringBuffer();
		for (Memoranda memo : memos){
			String title = memo.getTitle();
			String clock = "";
			if (memo.getWarn()){
				clock = "<img id='img_clock_" + memo.getId() + "' src='${ctx}/static/image/memoranda/clock.png' width='13px' height='13px' align='bottom'/>";
			}
			if (title.length() > 12){
				title = title.substring(0, 9) + "..."; 
			}
			memoSb.append("<div id='div_memoranda_memo_" + memo.getId() + "' class='a_memoranda_value'><span id='title_" + memo.getId() + "' class='span_title'><a class='a_title' id='a_title_" + memo.getId() + "' onclick='editMemoranda(" + memo.getId() + ");' style='cursor:pointer;' href='javascript:void(0);' title='" + memo.getTitle() + "'>" + title + "</a></span>" + clock + "<div></div></div>\n");
		}
		
		sb.append("  <td id='td_memoranda_" + position + day + "'>\n");
		sb.append("    <table width='100%' cellspacing='0' cellpadding='0' border='0'");
		if (position.equals("")){
			sb.append(" ondblclick='addMemoranda(" + day + ");'");
		}
		sb.append(">\n");
		sb.append("      <tr>\n");
		sb.append("        <td width='33%' valign='middle' height='20' align='center' style='border-bottom:#aaccee 1px solid;border-right:#aaccee 1px solid;background-color:#DCF0FB;");
		if (!position.equals("")){
			sb.append("color:#AAAAAA ");
		}
		sb.append("' id='td_table_memoranda_" + position + day + "'>" + day + " " + lunarValue + "<br></td>\n");
		sb.append("        <td width='67%' bgcolor='#E9F0F8'></td>\n");
		sb.append("      </tr>\n");
		sb.append("      <tr valign='top'>\n");
		sb.append("        <td height='65' onmouseover=this.bgColor='#EDFBD2' colspan='2' ");
		if (currentMonth == (month -1) && currentDay == day){
			sb.append(" onmouseout=this.bgColor='#FFFFCC' bgcolor='#FFFFCC' ");
		}else{
			sb.append(" onmouseout=this.bgColor='' ");
		}
		sb.append(">\n");
		sb.append("          <div id='div_memoranda_" + position + day + "' class='div_memoranda' style='cursor:pointer;width:auto;height:65px; overflow-y:auto; border:0px solid;'>\n");
		sb.append(memoSb.toString());
		sb.append("          </div>\n");
		sb.append("        </td>\n");
	    sb.append("      </tr>\n");
	    sb.append("    </table>\n");
		sb.append("  </td>\n");
		
		return sb;
	}

	private StringBuffer generatorFirstWeekHtml(Long userId, int firstDayOfMonth) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr class='memoranda_tr' valign='top'>\n");
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, selYear);
		if (prevMonth == 0){
			calendar.set(Calendar.YEAR, selYear - 1);
			prevMonth = 12;
		}
		calendar.set(Calendar.MONTH, prevMonth - 1);
		int prevMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int beginDay = prevMonthMaxDay - firstDayOfMonth + 2;
		for (int i = 1; i < firstDayOfMonth; i++){
			sb.append(generatorContentHtml(userId, calendar.get(Calendar.YEAR), prevMonth, beginDay, POSITION_PREV).toString());
			beginDay++;
		}
		for (int i = firstDayOfMonth; i<=7;i++){
			sb.append(generatorContentHtml(userId, selYear, selMonth, dayCount, "").toString());
			dayCount++;
		}
		sb.append("</tr>\n");

		return sb;
	}
	
	private StringBuffer generatorMiddleWeekHtml(Long userId, int week){
		StringBuffer sb = new StringBuffer();
		
		int middleWeek = week - 1;
		
		for (int j = 1; j < middleWeek; j++){
			sb.append("<tr class='memoranda_tr' valign='top'>\n");
			for (int i = 1; i <= 7; i++){
				sb.append(generatorContentHtml(userId, selYear, selMonth, dayCount, "").toString());
				dayCount++;
			}
			sb.append("</tr>\n");
		}
		return sb;
	}
	
	private StringBuffer generatorLastWeekHtml(Long userId, int days, int firstDayOfMonth){
		StringBuffer sb = new StringBuffer();
		
		int lastDays = days - dayCount + 1;
		
		sb.append("<tr class='memoranda_tr' valign='top'>\n");
		for (int i = 1; i <= lastDays; i++){
			sb.append(generatorContentHtml(userId, selYear, selMonth, dayCount, "").toString());
			dayCount++;
		}
		
		int blankDay = 7 - lastDays;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, selYear);
		if (nextMonth == 13){
			calendar.set(Calendar.YEAR, selYear + 1);
			nextMonth = 1;
		}

		for (int i = 1; i <= blankDay; i++){
			sb.append(generatorContentHtml(userId, calendar.get(Calendar.YEAR), nextMonth, i, POSITION_NEXT).toString());
		}
		sb.append("</tr>\n");
		
		return sb;
	}
	
	private StringBuffer getTitleHtml(){
		StringBuffer sb = new StringBuffer();
		sb.append("<tr class='memoranda_tr' bgcolor='#DCF0FB' align='center'>");
		sb.append("  <td width='15%' height='30'><font color='#14AD00'>星期日</font></td>");
		sb.append("  <td width='14%'>星期一</td>");
		sb.append("  <td width='14%'>星期二</td>");
		sb.append("  <td width='14%'>星期三</td>");
		sb.append("  <td width='14%'>星期四</td>");
		sb.append("  <td width='14%'>星期五</td>");
		sb.append("  <td width='15%'><font color='#14AD00'>星期六</font></td>");
		sb.append("</tr>");
		return sb;
	}
	
	private String getLunarDay(int year, int month, int day){
		return Lunar.getLunarDay(year, month, day);
	}
	
	private String getSolarTerms(int year, int month, int day){
		return SolarTerm.getSoralTerm(year, month, day);
	}

	public Memoranda save(Long userId, Memoranda memoranda, Integer year, Integer month, Integer day) {
		memoranda.setUserId(userId);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		memoranda.setNoteDate(calendar.getTime());
		
		if (memoranda.getWarn() && memoranda.getWarnTime() != null){
			setBefore(memoranda);
		}
		
		return super.save(memoranda);
	}

	@Override
	public Memoranda update(Memoranda memoranda) {
		if (memoranda.getWarn() && memoranda.getWarnTime() != null){
			setBefore(memoranda);
		}
		return super.update(memoranda);
	}

	public void update(Long memorandaId, Integer year, Integer month, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		
		Memoranda memoranda = findOne(memorandaId);
		memoranda.setNoteDate(calendar.getTime());
		
		update(memoranda);
	}
	
	private List<Memoranda> findMemorandaByDate(Long userId, Integer year, Integer month, Integer day) {
		SimpleDateFormat noteDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		Date beginDate = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date endDate = calendar.getTime();
		
		try {
			beginDate = noteDateFormat.parse(noteDateFormat.format(beginDate));
			endDate = noteDateFormat.parse(noteDateFormat.format(endDate));
		} catch (ParseException e) {
		}
		return getMemorandaRepository().findByNoteDateBetweenAndNoteDateBeforeAndUserIdOrderByWarnTimeDescIdDesc(beginDate, endDate, endDate, userId);
	}

	public List<Map<String, Object>> getMemorandaFireTime(Long userId, Date clientTime){
		Calendar c_before = Calendar.getInstance();
		Calendar c_after = Calendar.getInstance();
		
		c_before.setTime(clientTime);
		c_after.setTime(clientTime);
		
		c_before.add(Calendar.MINUTE, -1);
		c_after.add(Calendar.MINUTE, 1);
		
		Long beforeTime = c_before.getTime().getTime();
		Long afterTime = c_after.getTime().getTime();
		
		List<Memoranda> memorandaMsg = new ArrayList<Memoranda>();
		
		List<Memoranda> memorandas = getMemorandaRepository().findByUserIdAndWarnTrueAndWarnTimeIsNotNullAndNoteDateIsNotNull(userId);
		if (memorandas == null || memorandas.isEmpty()) return new ArrayList<Map<String, Object>>();
		for (Memoranda memoranda : memorandas){
			Date fireTime = memoranda.getFireTime();
			if (fireTime == null){
				setBefore(memoranda);
				super.save(memoranda);
			}
			Long fireTimeValue = memoranda.getFireTime().getTime();
			if (fireTimeValue.longValue() >= beforeTime && fireTimeValue.longValue() <= afterTime){
				memorandaMsg.add(memoranda);
				updateNextFireTime(memoranda.getId());
			}else if (fireTimeValue.longValue() < beforeTime ){
				if (memoranda.getMissRemind())
					memorandaMsg.add(memoranda);
				updateNextFireTime(memoranda.getId());
			}
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (Memoranda memoranda : memorandas){
			Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", memoranda.getId());
            map.put("title", memoranda.getTitle());
            map.put("content", memoranda.getContent());
            dataList.add(map);
		}
		return dataList;
	}
	
	private void updateNextFireTime(Long memorandaId){
		Memoranda memoranda = findOne(memorandaId);
		setFrequency(memoranda);
		super.save(memoranda);
	}
	
	private void setFrequency(Memoranda memoranda){
		Calendar calendar = Calendar.getInstance();

		Date fireTime = memoranda.getFireTime();
		if (fireTime == null){
			SimpleDateFormat notesDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String notesDateValue = notesDateFormat.format(memoranda.getNoteDate());
			SimpleDateFormat warnDateFormat = new SimpleDateFormat("HH:mm:00");
			String warnDateValue = warnDateFormat.format(memoranda.getWarnTime());
			
			String fireTimeValue = notesDateValue + " " + warnDateValue;
			SimpleDateFormat fireDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
			
			try {
				fireTime = fireDateFormat.parse(fireTimeValue);
			} catch (ParseException e) {
				fireTime = calendar.getTime();
			}
		}
		calendar.setTime(fireTime);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		
		FrequencyStatus status = memoranda.getFrequency();
		switch(status){
			case SINGLE://单次
				memoranda.setWarn(false);
				break;
			case EVERYDAY://每天
				calendar.set(year, month, day + 1);
				break;
			case EVERYWEEK://每周
				calendar.set(year, month, day + 7);
				break;
			case EVERYMONTHWEEK://每月(星期)
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
				break;
			case EVERYMONTH://每月(日)
				calendar.set(year, month + 1, day);
				break;
			case EVERYYEAR://每年
				calendar.set(year + 1, month , day);
				break;
			default:
				break;
		}
		memoranda.setFireTime(calendar.getTime());
	}
	
	private void setBefore(Memoranda memoranda){
		Calendar calendar = Calendar.getInstance();
		
		Date fireTime = memoranda.getFireTime();
		if (fireTime == null){
			SimpleDateFormat notesDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String notesDateValue = notesDateFormat.format(memoranda.getNoteDate());
			SimpleDateFormat warnDateFormat = new SimpleDateFormat("HH:mm:00");
			String warnDateValue = warnDateFormat.format(memoranda.getWarnTime());
			
			String fireTimeValue = notesDateValue + " " + warnDateValue;
			SimpleDateFormat fireDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
			
			try {
				fireTime = fireDateFormat.parse(fireTimeValue);
			} catch (ParseException e) {
				fireTime = calendar.getTime();
			}
		}
		calendar.setTime(fireTime);
		
		BeforeStatus status = memoranda.getBefore();
		switch(status){
			case NONE://正点
				break;
			case ONE://1分钟
				calendar.add(Calendar.MINUTE, -1);
				break;
			case FIVE://5分钟
				calendar.add(Calendar.MINUTE, -5);
				break;
			case TEN://10分钟
				calendar.add(Calendar.MINUTE, -10);
				break;
			case FIFTEEN://15分钟
				calendar.add(Calendar.MINUTE, -15);
				break;
			case TWENTY://20分钟
				calendar.add(Calendar.MINUTE, -20);
				break;
			case TWENTYFIVE://25分钟
				calendar.add(Calendar.MINUTE, -25);
				break;
			case THIRTY://30分钟
				calendar.add(Calendar.MINUTE, -30);
				break;
			case FORTYFIVE://45分钟
				calendar.add(Calendar.MINUTE, -45);
				break;
			case ONEHOUR://1小时
				calendar.add(Calendar.HOUR, -1);
				break;
			case TWOHOUR://2小时
				calendar.add(Calendar.HOUR, -2);
				break;
			case THREEHOUR://3小时
				calendar.add(Calendar.HOUR, -3);
				break;
			case TWELVEHOUR://12小时
				calendar.add(Calendar.HOUR, -12);
				break;
			case TWENTYFOUR://24小时
				calendar.add(Calendar.HOUR, -24);
				break;
			case TWODAY://2天
				calendar.add(Calendar.DATE, -2);
				break;
			case ONEWEEK://1星期
				calendar.add(Calendar.DATE, -7);
				break;
			default:
				break;
		}
		memoranda.setFireTime(calendar.getTime());
	}

	public List<Memoranda> findMemorandaByUserId(Long userId) {
		return getMemorandaRepository().findByUserIdOrderByNoteDateDescWarnTimeDescIdDesc(userId);
	}
	
}
