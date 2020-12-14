package com.example.test;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 图书管理系统的功能实现类
 */
public class BookDaoImpl implements BookDao{
    private static File sdpath= Environment.getExternalStorageDirectory();
    private  static File  file=new File(sdpath,"Library.txt");
    private  static File  file1=new File(sdpath,"temp.txt");

    //创建类的时候就创建文件
    static {
        try {
            if(file.exists()&file1.exists())
                System.out.println("文件已存在");
            else{
                file1.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    /**
     * 增添书籍
     * @param book 书的信息
     */
    @Override
    public void addBook(Book book) {
        BufferedWriter bw=null;
        BufferedReader br=null;

        try {
            bw=new BufferedWriter(new FileWriter(file,true));//true代表将数据写入文件末尾处，而不是文件开始处
            br=new BufferedReader(new FileReader(file));
            String line=null;
            if((line=br.readLine())!=null){
                //如果文件第一行不是空，即文件已经有内容
                bw.write("\r\n");//因为文件最后一行的末尾没有回车换行符，如果不先加回车换行符，就会直接在最后一行写，不会新增一行
                bw.write(book.getID()+"-"+book.getName()+"-"+book.getPrice());
                bw.flush();
            }else{//如果文件第一行为空，即文件没有内容
                bw.write(book.getID()+"-"+book.getName()+"-"+book.getPrice());
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(bw!=null&br!=null) {
                try {
                    bw.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 查询书籍
     * @param bookName 要查询的书籍名称
     */
    @Override
    public String findBook(String bookName) {
        BufferedReader br=null;
        String result=null;
        try {

            br=new BufferedReader(new FileReader(file));
            String readLine = "";
            while((readLine = br.readLine()) != null){
                String list[]=readLine.split("-");
                if(readLine.contains(bookName)) {
                    result="ID："+list[0]+"\n书名："+list[1]+"\n价格："+list[2];
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;


    }

    /**
     * 修改书籍信息
     * @param oldName 要修改的书籍名称
     * @param buffer  修改后的书籍全部信息，包括编号、书名、书的价格
     * @return
     */
    @Override
    public boolean alterBook(String oldName, String buffer) {
        RandomAccessFile raf=null;
        try {
            raf=new RandomAccessFile(file, "rw");
            String Line=null;

            while((Line=raf.readLine())!=null) {
                //RandomAccessFile 读写文件时，不管文件中保存的数据编码格式是什么
                // 使用 RandomAccessFile对象方法的 readLine() 都会将编码格式转换成 ISO-8859-1
                // 所以 输出显示是还要在进行一次转码
                String changeLine=new String(Line.getBytes("ISO-8859-1"), "utf-8");
                //查找要替换的内容
                if(changeLine.contains(oldName)) {
                    //得到当前指针的位置，如果不是最后一行，就应该是在oldName\r\n后面;
                    //如果是最后一行，则就在oldName后面
                    long lastpoint=raf.getFilePointer();

                    //如果是最后一行
                    if(lastpoint==raf.length()) {
                        //指针回到行首
                        raf.seek(lastpoint-changeLine.getBytes().length);
                        int currentLength=changeLine.getBytes().length;//原字符串的字节长度，1个中文字符占2字节
                        int targeLength=buffer.getBytes().length;//替换后字符串的字节长度

                        //替换的字符串字节数比源字符串小
                        if(currentLength>targeLength) {
                            int tempLength=currentLength-targeLength;
                            StringBuilder sb=new StringBuilder(buffer);
                            //目的是防止修改的内容没有没全替换掉

                            for(int a=0;a<tempLength;a++) {
                                sb.append(" ");
                            }
                            raf.writeBytes(new String(sb.toString().getBytes("utf-8"), "ISO-8859-1"));
                        }else {
                            //替换的字符串字节数比源字符串大或两者相等
                            raf.writeBytes(new String(buffer.getBytes("utf-8"), "ISO-8859-1"));

                        }
                    }else {//不是最后一行
                        //回到行首，减去2的原因是行末有\r\n，占2字节
                        raf.seek(lastpoint-changeLine.getBytes().length-2);
                        int currentLength=changeLine.getBytes().length;//原字符串的长度
                        int targeLength=buffer.getBytes().length;//替换后字符串的长度
                        //替换的字符串字节数比源字符串小，目的是防止修改的内容没有没全替换掉
                        if(currentLength>targeLength) {
                            int tempLength=currentLength-targeLength;
                            StringBuilder sb=new StringBuilder(buffer);
                            //源字符串没替换的字符用空格代替
                            for(int a=0;a<tempLength;a++) {
                                sb.append(" ");
                            }

                            raf.writeBytes(new String(sb.toString().getBytes("utf-8"), "ISO-8859-1"));
                        }else {
                            //替换的字符串比源字符串大或两者相等
                            raf.writeBytes(new String(buffer.getBytes("utf-8"), "ISO-8859-1"));
                        }

                    }
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(raf!=null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;


    }

    /**
     * 删除书籍
     * @param bookName 要删除的书籍名称
     * @param ID 要删除的书籍编号
     */
    @Override
    public void cancelBook(String bookName, String ID) {
        boolean flag=false;
        BufferedWriter bw=null;
        BufferedReader br=null;
        StringBuilder sb=null;
        try {
            bw=new BufferedWriter(new FileWriter(file1));
            br=new BufferedReader(new FileReader(file));
            String line=null;
            sb=new StringBuilder();
            while((line=br.readLine())!=null) {
                //要删除的那一行
                if (line.contains(bookName)) {
                    String[] data = line.split("-");//将书籍编号、书籍名、书籍价格存在数组中
                    if (!ID.equals(data[0]))
                        System.out.println("输入编号不正确");
                    else {
                        //如果删除的书籍信息都匹配成功
                        flag = true;

                    }
                }else {//不是要删除的那一行
                    sb.append(line).append("\r\n");//存储没有被删的书籍信息到字符串sb中
                }
            }
            int lastindex=sb.lastIndexOf("\r\n");//获取最后一行书籍信息的回车换行符索引
            sb.delete(lastindex,sb.length());//删除最后一行书籍信息的回车换行符

            //如果输入的删除信息都匹配成功，就开始删除信息
            if(flag) {
                bw.write(sb.toString());//将字符串写进新的文件中
                //重命名文件必须先关闭流
                bw.close();
                br.close();
                if(file.delete())//重命名前必须删除同名文件
                    file1.renameTo(file);

            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null&bw!=null) {
                try {
                    bw.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
