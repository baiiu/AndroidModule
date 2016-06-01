println "hello groovy"

println("============== List ===========================");


def list = [1,'2',true,"stringFour",null];//定义数组
println list.size();//打印长度


try{
  println list.get(-1);
}catch(e){
  println(e.toString());
}

println list[-2];//倒数第二个
println list.getAt(-2);//使用getAt()方法倒数，只能为负数

println("------ 集合遍历 ------");

//集合遍历
list.each{
  println "Item $it" //it指代当前元素 `it` is an implicit parameter corresponding to the current element
}

list.eachWithIndex{it,i ->
  println("$i : $it");
}


println("=================== Map ======================");
def map = [name:"zhangsan", "sex":true, id:123];
println(map.size());

println map.get("name");//获取值
println map.name;//获取值


map.addone = "addedOne";//添加值
map.put("aa",false);//添加值

println(map.size());

println("------ 集合遍历 ------");

// `entry` is a map entry
map.each { entry ->
    println "Name: $entry.key Age: $entry.value"
}

// `entry` is a map entry, `i` the index in the map
map.eachWithIndex { entry, i ->
    println "$i - Name: $entry.key Age: $entry.value"
}

// Alternatively you can use key and value directly
map.each { key, value ->
    println "Name: $key Age: $value"
}

// Key, value and i as the index in the map
map.eachWithIndex { key, value, i ->
    println "$i - Name: $key Age: $value"
}


println("=================== Range ======================");
def range = 5..<8;
println ("size = " + range.size());

range.each{
  println("$it ," );
}

println range.from +", " +range.to;



















//e
