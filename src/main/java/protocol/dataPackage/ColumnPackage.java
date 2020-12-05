package protocol.dataPackage;

import io.MysqlByteArrayInputStream;

import java.io.IOException;

/**
 * catalog -目录（总是 "def"）
 * <p>
 * schema -模式名称
 * <p>
 * table -虚拟表名
 * <p>
 * org_table -物理表名
 * <p>
 * name -虚拟列名
 * <p>
 * org_name -物理列名
 * <p>
 * next_length -以下字段的长度（总是 0x0c）
 * <p>
 * character_set -是列字符集，在中定义 Protocol::CharacterSet。
 * <p>
 * column_length -字段的最大长度
 * <p>
 * column_type -列类型中定义的 列类型
 * <p>
 * flags -标志
 * <p>
 * decimals -最多显示十进制数字
 * <p>
 * filter -填充
 */
public class ColumnPackage {

    String catalog;
    String schema;
    String table;
    String org_table;
    String name;
    String org_name;
    String fixedLength;
    int characterSet;
    int columnLength;
    int type;
    int flags;
    int decimals;
    int filler;

    public ColumnPackage(byte[] bytes) throws IOException {
        MysqlByteArrayInputStream buffer = new MysqlByteArrayInputStream(bytes);
        catalog = buffer.readLengthEncodedString();
        schema = buffer.readLengthEncodedString();
        table = buffer.readLengthEncodedString();
        org_table = buffer.readLengthEncodedString();
        name = buffer.readLengthEncodedString();
        fixedLength = buffer.readLengthEncodedString();
        characterSet = buffer.readInteger(2);
        columnLength = buffer.readInteger(4);
        type = buffer.readInteger(1);
        flags = buffer.readInteger(2);
        decimals = buffer.readInteger(1);
        filler = buffer.readInteger(2);
    }


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOrg_table() {
        return org_table;
    }

    public void setOrg_table(String org_table) {
        this.org_table = org_table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getFixedLength() {
        return fixedLength;
    }

    public void setFixedLength(String fixedLength) {
        this.fixedLength = fixedLength;
    }

    public int getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(int characterSet) {
        this.characterSet = characterSet;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public int getFiller() {
        return filler;
    }

    public void setFiller(int filler) {
        this.filler = filler;
    }

    @Override
    public String toString() {
        return "ColumnPackage{" +
                "catalog='" + catalog + '\'' +
                ", schema='" + schema + '\'' +
                ", table='" + table + '\'' +
                ", org_table='" + org_table + '\'' +
                ", name='" + name + '\'' +
                ", org_name='" + org_name + '\'' +
                ", fixedLength='" + fixedLength + '\'' +
                ", characterSet=" + characterSet +
                ", columnLength=" + columnLength +
                ", type=" + type +
                ", flags=" + flags +
                ", decimals=" + decimals +
                ", filler=" + filler +
                '}';
    }
}
