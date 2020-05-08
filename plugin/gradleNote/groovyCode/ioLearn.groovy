
//创建文件对象
def file = new File("/Users/baiiu/gradleLearn/README.md");

//读取文件，查看API文档，熟悉用法
def aClosure = {
  line ->
  println "方法1：正统写法 "
  println line;
};
file.eachLine(aClosure);

println("======================================");

//打印行号
file.eachLine{
  line,nb ->
  println "方法2：两个参数，打印行号 "
  println "Line $nb : $line "
}

println("======================================");


file.withInputStream{
  is ->
  println "方法3：使用闭包操作IO流"
  println is.getText(); //查看API文档，操作输入流
};


//直接得到文件的内容
file.getBytes();

//获取文件输入流
def is = file.newInputStream();
is.close();//需要关闭


//使用闭包操作输入流
/*
file.withInputStream{
  is ->
  //.... 不需要close
};
*/

println("================ 写文件 ======================");
def newFile = new File("/Users/baiiu/Desktop/target.txt ");

newFile.withOutputStream{  os ->
  os << file.getBytes();
}
