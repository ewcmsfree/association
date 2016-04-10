package org.apache.shiro;

/**
 * @author wu_zhijun
 */
public interface ShiroConstants {
    /**
     * 当前在线会话
     */
    String ONLINE_SESSION = "online_session";

    /**
     * 仅清空本地缓存 不清空数据库的
     */
    String ONLY_CLEAR_CACHE = "online_session_only_clear_cache";
}
