def text = '''
    <some>
        <technology name="groovyProperty">
            <name>GroovyNode</name>
        </technology>
    </some>
'''

//使用XmlSlurper解析
def some = new XmlSlurper().parseText(text); //根节点
assert some instanceof groovy.util.slurpersupport.GPathResult
println some.technology.name;
println some.technology[0].@name;


//使用XmlParser解析,需要调动text()方法
def list = new XmlParser().parseText(text)
assert list instanceof groovy.util.Node
println list.technology.name.text() == 'GroovyNode'


def file = new File("/Users/baiiu/gradleLearn/assets/sample.xml");
println "===============XmlSlurper解析========================"

def response = new XmlSlurper().parse(file);

//获取book[3]的名字
println response.value.books.book[3].title
//获取book[3]的available属性
println response.value.books.book[3].@available



println "===============XmlSlurper解析========================"
def responseNode = new XmlParser().parse(file);
println responseNode.value.books.book[3].title.text();//调用text方法
println responseNode.value.books.book[3].@available
