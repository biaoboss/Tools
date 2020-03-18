package encoded;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * 对java文件的编码格式进行转码，解决乱码问题
 * 
 * @author biao boss
 */
public class Transcoding {

    /**
     * 对file 文件进行转码
     * 
     * @param codeBefore --当前文件的编码格式
     * @param codeAfter  --转换后的文件编码格式
     * @param file       --转码的文件
     */
    public static void codedConversion(String codeBefore, String codeAfter, File file) {
        
        StringBuffer content = new StringBuffer();
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            //创建字符缓冲输入流对象
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), codeBefore));
            String line = br.readLine();
            //以行为单位，循环读出文件的内容，存放在 content对象中
            while (line != null) {
                content.append(line + "\n");
                line = br.readLine();
            }
            //创建字符缓冲输出流对象
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), codeAfter));
            //将 content对象 的内容写入到文件中
            bw.write(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取file目录下的所有java文件
     * 
     * @param file  需转码的文件或文件夹
     * @param files 获取文件夹下和所有子文件夹下的java文件
     */
    public static void getAllFile(File file, Vector<File> files) {
        
        File[] fileList = null;
        //如果file文件夹，遍历文件夹下的所有文件，筛选所有java文件，将其存放到 fileList变量中
        if (file.isDirectory()) {
            fileList = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return true;
                    }
                    return pathname.getName().endsWith(".java");

                }
            });
        }else {
            //如果file为java文件，将其存放到 fileList变量中
            if (!file.getName().endsWith(".java")) {
                return ;
            }
            fileList = new File[1];
            fileList[0] = file;
        }
        //  遍历fileList 中的所有文件
        for (File f : fileList) {
            //如果 f 为文件夹，递归该方法
            if (f.isDirectory()) {
                getAllFile(f, files);
            }//否则 将f 文件 存放到 files 集合中
            else {
                files.add(f);
            }
        }

        return ;

    }
    
}
