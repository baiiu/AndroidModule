
//创建闭包
def code = {123};

//调用闭包
println code();
println code.call();


//创建带参数闭包
def isOddNumber = {
  int i ->
  i%2 == 1;//可以省略return语句
};
println isOddNumber(3);


//参数说明
def closureWithTwoArgs = { int a, int b -> a+b };
println closureWithTwoArgs(1,2);


//隐式参数
def greeting = { "Hello, $it!" };
println greeting("Patrick");

def greetingT = {
	it ->
	return "Hello, " + it + "!";
}
println greetingT("Patrick");


def isOddNumberT = { it % 2 == 1};
println isOddNumberT(3);


//可变参数
def aMethod = {
  int... args ->
  int sum = 0;
  args.eachWithIndex{
  it,i ->
  sum = sum + args[i];
  }
  return sum;
};
println aMethod(1,2,3);
