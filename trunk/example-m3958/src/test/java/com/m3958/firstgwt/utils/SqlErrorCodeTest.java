package com.m3958.firstgwt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

/*	
字符 
x 字符 x 
\\ 反斜线字符 
\0n 带有八进制值 0 的字符 n (0 <= n <= 7) 
\0nn 带有八进制值 0 的字符 nn (0 <= n <= 7) 
\0mnn 带有八进制值 0 的字符 mnn（0 <= m <= 3、0 <= n <= 7） 
\xhh 带有十六进制值 0x 的字符 hh 
\Uhhhh 带有十六进制值 0x 的字符 hhhh 
\t 制表符 ('\u0009') 
\n 新行（换行）符 ('\u000A') 
\r 回车符 ('\u000D') 
\f 换页符 ('\u000C') 
\a 报警 (bell) 符 ('\u0007') 
\e 转义符 ('\u001B') 
\cx 对应于 x 的控制符 
  
字符类 
[abc] a、b 或 c（简单类） 
[^abc] 任何字符，除了 a、b 或 c（否定） 
[a-zA-Z] a 到 z 或 A 到 Z，两头的字母包括在内（范围） 
[a-d[m-p]] a 到 d 或 m 到 p：[a-dm-p]（并集） 
[a-z&&[def]] d、e 或 f（交集） 
[a-z&&[^bc]] a 到 z，除了 b 和 c：[ad-z]（减去） 
[a-z&&[^m-p]] a 到 z，而非 m 到 p：[a-lq-z]（减去） 
  
预定义字符类 
. 任何字符（与行结束符可能匹配也可能不匹配） 
\d 数字：[0-9] 
\D 非数字： [^0-9] 
\s 空白字符：[ \t\n\x0B\f\r] 
\S 非空白字符：[^\s] 
\w 单词字符：[a-zA-Z_0-9] 
\W 非单词字符：[^\w] 
  
POSIX 字符类（仅 US-ASCII） 
\p{Lower} 小写字母字符：[a-z] 
\p{Upper} 大写字母字符：[A-Z] 
\p{ASCII} 所有 ASCII：[\x00-\x7F] 
\p{Alpha} 字母字符：[\p{Lower}\p{Upper}] 
\p{Digit} 十进制数字：[0-9] 
\p{Alnum} 字母数字字符：[\p{Alpha}\p{Digit}] 
\p{Punct} 标点符号：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ 
\p{Graph} 可见字符：[\p{Alnum}\p{Punct}] 
\p{Print} 可打印字符：[\p{Graph}\x20] 
\p{Blank} 空格或制表符：[ \t] 
\p{Cntrl} 控制字符：[\x00-\x1F\x7F] 
\p{XDigit} 十六进制数字：[0-9a-fA-F] 
\p{Space} 空白字符：[ \t\n\x0B\f\r] 
  
java.lang.Character 类（简单的 java 字符类型） 
\p{javaLowerCase} 等效于 java.lang.Character.isLowerCase() 
\p{javaUpperCase} 等效于 java.lang.Character.isUpperCase() 
\p{javaWhitespace} 等效于 java.lang.Character.isWhitespace() 
\p{javaMirrored} 等效于 java.lang.Character.isMirrored() 
  
Unicode 块和类别的类 
\p{InGreek} Greek 块（简单块）中的字符 
\p{Lu} 大写字母（简单类别） 
\p{Sc} 货币符号 
\P{InGreek} 所有字符，Greek 块中的除外（否定） 
[\p{L}&&[^\p{Lu}]]  所有字母，大写字母除外（减去） 
  
边界匹配器 
^ 行的开头 
$ 行的结尾 
\b 单词边界 
\B 非单词边界 
\A 输入的开头 
\G 上一个匹配的结尾 
\Z 输入的结尾，仅用于最后的结束符（如果有的话） 
\z 输入的结尾 
  
Greedy 数量词 
X? X，一次或一次也没有 
X* X，零次或多次 
X+ X，一次或多次 
X{n} X，恰好 n 次 
X{n,} X，至少 n 次 
X{n,m} X，至少 n 次，但是不超过 m 次 
  
Reluctant 数量词 
X?? X，一次或一次也没有 
X*? X，零次或多次 
X+? X，一次或多次 
X{n}? X，恰好 n 次 
X{n,}? X，至少 n 次 
X{n,m}? X，至少 n 次，但是不超过 m 次 
  
Possessive 数量词 
X?+ X，一次或一次也没有 
X*+ X，零次或多次 
X++ X，一次或多次 
X{n}+ X，恰好 n 次 
X{n,}+ X，至少 n 次 
X{n,m}+ X，至少 n 次，但是不超过 m 次 
  
Logical 运算符 
XY X 后跟 Y 
X|Y X 或 Y 
(X) X，作为捕获组 
  
Back 引用 
\n 任何匹配的 nth 捕获组 
  
引用 
\ Nothing，但是引用以下字符 
\Q Nothing，但是引用所有字符，直到 \E 
\E Nothing，但是结束从 \Q 开始的引用 
  
特殊构造（非捕获） 
(?:X) X，作为非捕获组 
(?idmsux-idmsux)  Nothing，但是将匹配标志由 on 转为 off 
(?idmsux-idmsux:X)   X，作为带有给定标志 on - off 的非捕获组 
(?=X) X，通过零宽度的正 lookahead 
(?!X) X，通过零宽度的负 lookahead 
(?<=X) X，通过零宽度的正 lookbehind 
(?<!X) X，通过零宽度的负 lookbehind 
(?>X) X，作为独立的非捕获组 
*/


//matches 方法尝试将整个输入序列与该模式匹配。 
//
//lookingAt 尝试将输入序列从头开始与该模式匹配。 
//
//find 方法扫描输入序列以查找与该模式匹配的下一个子序列。 


public class SqlErrorCodeTest extends TestCase {

	@Test
	public void testDup(){
		String es = "Exception [EclipseLink-4002] (Eclipse Persistence Services - 2.0.2.v20100323-r6872): org.eclipse.persistence.exceptions.DatabaseException" +
				"Internal Exception: com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException: Column 'loginName' cannot be null" +
				"Error Code: 1048" +
				"Call: INSERT INTO users (ID, PASSWORD, PASSWORDHINT, MOBILE, LOGINNAME, ADDRESS, CREATEDAT, BIRTHDAY, FIRSTNAME, VERSION, LASTNAME, OPENID, EMAIL, PHONENUMBER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
				"bind => [255, null, null, null, null, null, null, 2010-04-29, null, 1, null, null, null, null]" +
				"Query: InsertObjectQuery(com.m3958.firstgwt.model.User@38a6d2)";
		boolean  b =  es.matches("Error\\s+Code:\\s+1048\\D");
		assertNotNull(b);
		
		Pattern p = Pattern.compile("Column\\s+'(\\S+)'\\s+[a-zA-Z\\s]+null");
		Matcher m = p.matcher(es);
		boolean b1 = m.find();
		assertNotNull(b1);
		assertEquals("loginName", m.group(1));
		
	}

}
