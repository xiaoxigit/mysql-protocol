package protocol.dataPackage;


import io.MysqlByteArrayInputStream;

import java.io.IOException;
/**
 * 1              [0a] protocol version
 * string[NUL]    server version
 * 4              connection id
 * string[8]      auth-plugin-data-part-1
 * 1              [00] filler
 * 2              capability flags (lower 2 bytes)
 * if more data in the packet:
 * 1              character set
 * 2              status flags
 * 2              capability flags (upper 2 bytes)
 * if capabilities & CLIENT_PLUGIN_AUTH {
 * 1              length of auth-plugin-data
 * } else {
 * 1              [00]
 * }
 * string[10]     reserved (all [00])
 * if capabilities & CLIENT_SECURE_CONNECTION {
 * string[$len]   auth-plugin-data-part-2 ($len=MAX(13, length of auth-plugin-data - 8))
 * if capabilities & CLIENT_PLUGIN_AUTH {
 * string[NUL]    auth-plugin name
 * }
 */

/**
 * protocol_version （1） 0x0a-protocol_version
 * server_version （string.NUL）-可读的服务器版本
 * connection_id （4）-连接ID
 * auth_plugin_data_part_1 （ string.fix_len）-[len = 8]身份验证插件数据的前8个字节
 * Filler_1 （1）- 0x00
 * Capacity_flag_1 （2）-的低2个字节 Protocol::CapabilityFlags （可选）
 * character_set （1）-默认服务器字符集，仅低8位 Protocol::CharacterSet （可选）
 * 该“字符集”值实际上是排序规则ID，但暗含了字符集。请参阅 Protocol::CharacterSet 说明。
 * status_flags （2）- Protocol::StatusFlags （可选）
 * Capacity_flags_2 （2）-的高2字节 Protocol::CapabilityFlags
 * auth_plugin_data_len （1）-组合的auth_plugin_data的长度，如果auth_plugin_data_len> 0
 * auth_plugin_name （string.NUL）-auth_plugin_data所属的auth_method的名称
 */
public class GreetingPacket {

    private int protocolVersion;
    private String serverVersion;
    private long threadId;
    private String scramble;
    private int serverCapabilities;
    private int serverCollation;
    private int serverStatus;
    private String pluginProvidedData;

    public GreetingPacket(byte[] bytes) throws IOException {
        MysqlByteArrayInputStream buffer = new MysqlByteArrayInputStream(bytes);
        this.protocolVersion = buffer.readInteger(1);
        this.serverVersion = buffer.readZeroTerminatedString();
        this.threadId = buffer.readLong(4);
        String scramblePrefix = buffer.readZeroTerminatedString();
        this.serverCapabilities = buffer.readInteger(2);
        this.serverCollation = buffer.readInteger(1);
        this.serverStatus = buffer.readInteger(2);
        buffer.skip(13); // reserved
        this.scramble = scramblePrefix + buffer.readZeroTerminatedString();
        if (buffer.available() > 0) {
            this.pluginProvidedData = buffer.readZeroTerminatedString();
        }
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }

    public int getServerCapabilities() {
        return serverCapabilities;
    }

    public void setServerCapabilities(int serverCapabilities) {
        this.serverCapabilities = serverCapabilities;
    }

    public int getServerCollation() {
        return serverCollation;
    }

    public void setServerCollation(int serverCollation) {
        this.serverCollation = serverCollation;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getPluginProvidedData() {
        return pluginProvidedData;
    }

    public void setPluginProvidedData(String pluginProvidedData) {
        this.pluginProvidedData = pluginProvidedData;
    }

    @Override
    public String toString() {
        return "GreetingPacket{" +
                "protocolVersion=" + protocolVersion +
                ", serverVersion='" + serverVersion + '\'' +
                ", threadId=" + threadId +
                ", scramble='" + scramble + '\'' +
                ", serverCapabilities=" + serverCapabilities +
                ", serverCollation=" + serverCollation +
                ", serverStatus=" + serverStatus +
                ", pluginProvidedData='" + pluginProvidedData + '\'' +
                '}';
    }
}
