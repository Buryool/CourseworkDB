//import java.sql.*;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Vector;
//
///**
// * 这个类提供了从数据库中检索各种数据的功能。
// * 这个类依赖于DBUtil类
// */
//public class RetrieveDB {
//
//    private Connection dbConnection;
//
//    public List<List> getDataFromCiteDocList() {
//        List<List> columns = new ArrayList<List>();
//        List<String> row;
//
//        try {
//            dbConnection = DBUtil.getLocalConnection();
//            if (dbConnection == null) {
//                return null;
//            }
//
//            //准备SQL查询-回顾您在数据库模块中学到的内容。
//            //这只是一个示例，您需要为您的数据库创建真正的查询。
//            String query = "Select id, authors, pub_time, title, freq from citation_doc_freqs "
//                    + //"where freq >= 2 " +
//                    "order by freq DESC;";
//
//            Statement statement = dbConnection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            List<List> resultVct = DBUtil.getResult(resultSet);
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            List<String> titleCol = DBUtil.getColumnNames(rsmd);
//            resultVct.add(0, titleCol);
//
//            return resultVct;
//        } catch (Exception e) {
//            System.out.println("Database operation problem occurred!");
//            e.printStackTrace();
//        } finally {
//
//            DBUtil.close(dbConnection);
//        }
//        return columns;
//    }
//
//    /**
//     * @param docID 从表中派生的引用文档的ID <B>citation_doc_freqs</B>.
//     * @return 与输入docID匹配的条目中的行
//     */
//    public List<List> getDataFromCiteSents(String docID) {
//
//        List<List> columns = new ArrayList<List>();
//        List<String> row;
//
//        try {
//            dbConnection = DBUtil.getLocalConnection();
//            if (dbConnection == null) {
//                return null;
//            }
//
//            //这只是一个示例，您需要为您的数据库创建真正的查询。
//            String query = "select id, sent_markup, file, opol, ref_id from citation_sents "
//                    + "where cite_doc_id = '" + docID + "' order by file;";
//
//            System.out.println("query: " + query);
//
//            Statement statement = dbConnection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            if (resultSet == null) {
//                System.out.println("Songthing wrong here!");
//            }
//
//            List<List> resultVct = DBUtil.getResult(resultSet);
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            List<String> titleCol = DBUtil.getColumnNames(rsmd);
//            resultVct.add(0, titleCol);
//
//            return resultVct;
//
//        } catch (Exception e) {
//            System.out.println("Database operation problem occurred!");
//            e.printStackTrace();
//        } finally {
//
//            DBUtil.close(dbConnection);
//        }
//
//        return columns;
//    }
//
//    /**列出文集中引用的文献以及相关信息，如标题、作者等
//     * @param formatFlag 输出格式标志:1——纯文本，2——HTML表。
//     * @param dbResult 向量的向量中检索数据的结果。
//     * @return 字符串格式的结果。
//     */
//    public String formatResult(int formatFlag, List<List> dbResult) {
//
//        List<List> columns = dbResult;
//        StringBuffer sb = new StringBuffer();
//        //返回纯文本
//        if (formatFlag == 1) {
//            for (int i = 0; i < columns.size(); i++) {
//                List<String> row = columns.get(i);
//                for (int j = 0; j < row.size(); j++) {
//                    String item = row.get(j);
//                    sb.append(item + "\t");
//
//                }
//                sb.append("\n");
//            }
//        } else { //创建HTML表
//            sb.append("<TABLE ALIGN=\"CENTER\" BORDER=\"1\">");
//            //DB- id仅用于实验中检查DB源。
//            sb.append("<TR BGCOLOR=\"#FFFFCC\"><TH>DB_ID</TH><TH>Authors</TH><TH>Pub_Year</TH><TH>Cited Document Title</TH><TH>Freq</TH></TR>");
//
//            for (int i = 1; i < columns.size(); i++) {
//                sb.append("<TR>");
//                List<String> row = columns.get(i);
//                String id = row.get(0);
//                for (int j = 0; j < row.size(); j++) {
//                    String item = row.get(j);
//                    if (j == 1) {
//                        if (item.indexOf(";") < 0) {
//                            sb.append("<TD>" + item + "</TD>");
//                        } else {
//                            sb.append("<TD>" + item.substring(0, item.indexOf(";")) + " <I>et al.</I></TD>");
//                        }
//                    } else if (j == 3) //If this is title
//                    {
//                        sb.append("<TD><A HREF=\"cite_polarity?id=" + id + "&title=" + item + "\">" + item + "</A></TD>");
//                    } else if (j == 4) {
//                        sb.append("<TD ALIGN=\"right\">" + item + "</TD>");
//                    } else {
//                        sb.append("<TD ALIGN=\"middle\">" + item + "</TD>");
//                    }
//                }
//                sb.append("</TR>");
//            }
//
//            sb.append("</TABLE>");
//
//        }
//
//
//        return sb.toString();
//    }
//
//    /**列出包含引用和相关信息的句子。
//     * @param dbResult 在Vector of Vectors中从数据库中提取句子。
//     * @return 字符串格式的结果。
//     */
//    public String formatResultSents(List<List> dbResult) {
//        List<List> columns = dbResult;
//        StringBuffer sb = new StringBuffer();
//        //返回纯文本
//        //创建HTML表
//        sb.append("<TABLE ALIGN=\"CENTER\" BORDER=\"1\">");
//        sb.append("<TR BGCOLOR=\"#FFFFCC\"><TH>No</TH><TH>DB_id</TH><TH>Sentence Containing Citation</TH><TH>Source file</TH><TH>Opinion polarity</TH></TR>");
//
//        String prevFileName = "";
//        //字符串非白色= " # FFFFEE ";
//        String nonWhite = "#EEFFDF";
//
//        String bgColor = nonWhite;
//        for (int i = 1; i < columns.size(); i++) {
//
//            List<String> row = columns.get(i);
//
//            //如果不同的文档，给出不同的颜色——容易识别句子来自相同的文档
//            String fileName = row.get(2);
//            if (!fileName.equals(prevFileName)) {
//                bgColor = (bgColor.equals(nonWhite) ? "white" : nonWhite);
//            }
//
//            sb.append("<TR bgColor=\"" + bgColor + "\">");
//            sb.append("<TD ALIGN=\"right\">" + i + "</TD>");
//
//
//
//            //检索数据库记录项:
//            //1) 添加数据库ID
//            sb.append("<TD>" + row.get(0) + "</TD>");
//            //2)添加句子
//            String snt = row.get(1);
//            if (snt.trim().equals("")) {
//                sb.append("<TD ALIGN=\"middle\">" + "<Font color=\"indigo\">Parsing Unsuccessful</Font>" + "</TD>");
//            } else {
//
//                System.out.println(snt);
//                //2.1)标记的引用
//                String refId = row.get(4);
//                int cite_start = snt.indexOf(refId);
//                int cite_end = cite_start + refId.length();
//                snt = snt.substring(0, cite_start) + "<A TITLE=\"cited doc ID\"><B><I>" + refId + "</I></B></A>" + snt.substring(cite_end);
//                //Enju解析器不把句子结尾的句号括在&lt;word&gt;标记，所以暂时添加它。
//                //当解析器问题得到修复时，应该删除它。
//                sb.append("<TD ALIGN=\"middle\">" + snt + ".</TD>");
//            }
//            //3) 添加源文件名
//            sb.append("<TD><A HREF=\"http://text0.mib.man.ac.uk/~spiao/corpora/plos/sample_docs/" + fileName + "\">" + fileName + "</A></TD>");
//            //4) 增加意见极性得分
//            sb.append("<TD ALIGN=\"right\">" + row.get(3) + "</TD>");
//            sb.append("</TR>");
//            prevFileName = fileName;
//
//        }
//
//        sb.append("</TABLE>");
//
//        return sb.toString();
//    }
//
//
//
//    public static void main(String args[]) {
//
//        RetrieveDB app = new RetrieeveDB();
//
//        Vector<Vector> resultInVct = app.getDataFromDB();
//        String resultInHTML= app.formatResult(1, resultInVct);
//
//        System.out.println(resultInHTML);
//        System.exit(0);
//    }
//
//}
