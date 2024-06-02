package sagiri;

/**
 * The url mapping.
 * @author Sagiri
 */
public interface UrlMapping {
    /**
     * URL 固定前缀.
     */
    String PREFIX = "";


    // -------------------------------- Jdbc 调用相关接口 --------------------------------
    /**
     * 运行 Jdbc 调用工具.
     */
    String INVOKE_JDBC_EXECUTE = PREFIX + "/invoke/jdbc/execute";
    // -------------------------------- Jdbc 调用相关接口 --------------------------------


    // -------------------------------- Http 调用相关接口 --------------------------------
    /**
     * 运行接口调用工具.
     */
    String INVOKE_HTTP_EXECUTE = PREFIX + "/invoke/http/execute";
    // -------------------------------- Http 调用相关接口 --------------------------------


    // -------------------------------- 脚本调用相关接口 --------------------------------
    /**
     * 运行脚本调用工具.
     */
    String INVOKE_SCRIPT_EXECUTE = PREFIX + "/invoke/script/execute";
    // -------------------------------- 脚本调用相关接口 --------------------------------


}
