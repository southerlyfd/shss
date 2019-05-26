package com.jianghongbo.common.util;

import com.jianghongbo.common.consts.CommonConst;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianghongbo.common.interceptor.AuthenticationInterceptor;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-31 18:59
 * @description：
 */
@Slf4j
public class StringUtil extends org.springframework.util.StringUtils {

    public static boolean isBlank(String str) {
        str = StringUtils.isBlank(str) ? "" : str.trim();
        return str.length() > 0 ? false : true;
    }

	public static String trimNull(String str) {
		return isBlank(str) ? "" : str.trim();
	}

    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date);
    }
	/**
	 * 生成token
	 * @return
	 */
	public static String getToken() {
		String token = StringUtil.getCurrentDateTime() + "_" + UUID.randomUUID() + "";
		return token;
	}

    /**
     * 校验token是否过期
     * @param shssToken
     * @return
     */
    public static boolean checkTokenExpiration(String shssToken) {
    	if (shssToken == null || "".equals(shssToken)) {
			return false;
		}
    	String tokenDataStr = shssToken.split("_")[0]; // 生成token时间
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	Date tokenDate = null;
    	try {
			tokenDate = sdf.parse(tokenDataStr);
		} catch (ParseException e) {
			log.info("token值不合法：info");
			log.debug(("token值不合法：debug"));;
			throw new ShssException(StateCodeConstant.ERROR_TOKEN_INVALID, CommonConst.TOKEN_WRONGFUL);
		}
    	Calendar c = Calendar.getInstance();
    	c.setTime(tokenDate);
    	c.add(Calendar.DAY_OF_MONTH, 70);
    	if (c.getTime().compareTo(new Date()) >= 0) {
			return true;
		}
    	return false;
    }
    
//    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * JSON转Map
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2Map(String jsonStr){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> map = null;
		try {
			map = objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
//			logger.error("JSON转Map异常", e);
		}
		return map;
	}

	/**
	 * 用于实体类的toString方法
	 * @param object
	 * @return
	 */
	public static String objectToString(Object object){
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
//			logger.error("转JSON异常", e);
		}
		return json;
	}
	/**
	 * 使字符串第一个字符小写
	 * @param target
	 * @return
	 */
	public static String firstCharToLow(String target){
		String firstChar = null;
		if(target != null && !"".equalsIgnoreCase(target)){
			firstChar = target.substring(0, 1);
			target = firstChar.toLowerCase() + target.substring(1);
		}
		return target;
	}

	/**
	 * 使字符串数组转为list<String>
	 * @param target
	 * @return
	 */
	public static List<String> stringArr2List(String[] strArr){
		List<String> list = new ArrayList<String>();
		if(strArr != null){
			for(String str:strArr){
				list.add(str);
			}
		}
		return list;
	}

	/**
	 * 判断字符串是否为NULL或""
	 * @param str
	 * @return
	 */
	public static boolean isStrNull (String str){
		if(null == str || "".equals(str)){
			return true;
		}
		return false;
	}

	/**
     * 产生随机字符串
     */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	/**
	 * 产生随机字符串
	 * @param length 长度
	 * @return
	 */
	public static final String randomString(int length) {
	         if (length < 1) {
	             return null;
	         }
	         if (randGen == null) {
	                randGen = new Random();
	                numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
	                   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	                  //numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	                 }
	         char [] randBuffer = new char[length];
	         for (int i=0; i<randBuffer.length; i++) {
	             randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
	          //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
	         }
	         return new String(randBuffer);
	}


	  /**
	   * 左补齐一个特殊字符.
	   *
	   * Pad to a size of <code>size</code>.
	   *
	   * <pre>
	   * StringUtils.leftPad(null, *, *)     = null
	   * StringUtils.leftPad("", 3, 'z')     = "zzz"
	   * StringUtils.leftPad("bat", 3, 'z')  = "bat"
	   * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
	   * StringUtils.leftPad("bat", 1, 'z')  = "bat"
	   * StringUtils.leftPad("bat", -1, 'z') = "bat"
	   * </pre>
	   *
	   * @param str  the String to pad out, may be null
	   * @param size  the size to pad to
	   * @param padChar  the character to pad with
	   * @return left padded String or original String if no padding is necessary,
	   *  <code>null</code> if null String input
	   * @since 2.0
	   */
	  public static String leftPad(String str, int size, char padChar) {
	      if (str == null) {
	          return null;
	      }
	      int pads = size - str.length();
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      return padding(pads, padChar).concat(str);
	  }

	  /**
	   * 左补齐字符串
	   *
	   * Pad to a size of <code>size</code>.
	   *
	   * <pre>
	   * StringUtils.leftPad(null, *, *)      = null
	   * StringUtils.leftPad("", 3, "z")      = "zzz"
	   * StringUtils.leftPad("bat", 3, "yz")  = "bat"
	   * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
	   * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
	   * StringUtils.leftPad("bat", 1, "yz")  = "bat"
	   * StringUtils.leftPad("bat", -1, "yz") = "bat"
	   * StringUtils.leftPad("bat", 5, null)  = "  bat"
	   * StringUtils.leftPad("bat", 5, "")    = "  bat"
	   * </pre>
	   *
	   * @param str  the String to pad out, may be null
	   * @param size  the size to pad to
	   * @param padStr  the String to pad with, null or empty treated as single space
	   * @return left padded String or original String if no padding is necessary,
	   *  <code>null</code> if null String input
	   */
	  public static String leftPad(String str, int size, String padStr) {
	      if (str == null) {
	          return null;
	      }
	      if (isStrNull(padStr)) {
	          padStr = " ";
	      }
	      int padLen = padStr.length();
	      int strLen = str.length();
	      int pads = size - strLen;
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      if (padLen == 1) {
	          return leftPad(str, size, padStr.charAt(0));
	      }

	      if (pads == padLen) {
	          return padStr.concat(str);
	      } else if (pads < padLen) {
	          return padStr.substring(0, pads).concat(str);
	      } else {
	          char[] padding = new char[pads];
	          char[] padChars = padStr.toCharArray();
	          for (int i = 0; i < pads; i++) {
	              padding[i] = padChars[i % padLen];
	          }
	          return new String(padding).concat(str);
	      }
	  }

	  /**
	   * 右补齐一个特殊字符.
	   *
	   * Pad to a size of <code>size</code>.
	   *
	   * <pre>
	   * StringUtils.rightPad(null, *, *)     = null
	   * StringUtils.rightPad("", 3, 'z')     = "zzz"
	   * StringUtils.rightPad("bat", 3, 'z')  = "bat"
	   * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
	   * StringUtils.rightPad("bat", 1, 'z')  = "bat"
	   * StringUtils.rightPad("bat", -1, 'z') = "bat"
	   * </pre>
	   *
	   */
	  public static String rightPad(String str, int size, char padChar) {
	      if (str == null) {
	          return null;
	      }
	      int pads = size - str.length();
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      return str.concat(padding(pads, padChar));
	  }

	  /**
	   * 右补齐字符串
	   *
	   * Pad to a size of <code>size</code>.
	   *
	   * <pre>
	   * StringUtils.rightPad(null, *, *)      = null
	   * StringUtils.rightPad("", 3, "z")      = "zzz"
	   * StringUtils.rightPad("bat", 3, "yz")  = "bat"
	   * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
	   * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
	   * StringUtils.rightPad("bat", 1, "yz")  = "bat"
	   * StringUtils.rightPad("bat", -1, "yz") = "bat"
	   * StringUtils.rightPad("bat", 5, null)  = "  bat"
	   * StringUtils.rightPad("bat", 5, "")    = "  bat"
	   * </pre>
	   *
	   */
	  public static String rightPad(String str, int size, String padStr) {
	      if (str == null) {
	          return null;
	      }
	      if (isStrNull(padStr)) {
	          padStr = " ";
	      }
	      int padLen = padStr.length();
	      int strLen = str.length();
	      int pads = size - strLen;
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      if (padLen == 1) {
	          return rightPad(str, size, padStr.charAt(0));
	      }

	      if (pads == padLen) {
	          return str.concat(padStr);
	      } else if (pads < padLen) {
	          return str.concat(padStr.substring(0, pads));
	      } else {
	          char[] padding = new char[pads];
	          char[] padChars = padStr.toCharArray();
	          for (int i = 0; i < pads; i++) {
	              padding[i] = padChars[i % padLen];
	          }
	          return  str.concat(new String(padding));
	      }
	  }

	  /**
	   * 返回一个给定次数的重复字符串
	   * to a given length.
	   *
	   * <pre>
	   * StringUtils.padding(0, 'e')  = ""
	   * StringUtils.padding(3, 'e')  = "eee"
	   * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
	   * </pre>
	   *
	   * Note: this method doesn't not support padding with
	   * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
	   * as they require a pair of <code>char</code>s to be represented.
	   * If you are needing to support full I18N of your applications
	   * consider using {@link #repeat(String, int)} instead.
	   *
	   *
	   * @param repeat  number of times to repeat delim
	   * @param padChar  character to repeat
	   * @return String with repeated character
	   * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
	   * @see #repeat(String, int)
	   */
	  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
	      if (repeat < 0) {
	          throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
	      }
	      final char[] buf = new char[repeat];
	      for (int i = 0; i < buf.length; i++) {
	          buf[i] = padChar;
	      }
	      return new String(buf);
	  }

	/**
	 * 替换html标签特殊字符
	 * @param str
	 * @return str
	 */
	public static String replaceHtmlCh(String str){

		str = str.replace("&","&amp;");
        str = str.replace("<","&lt;");
        str = str.replace(">","&gt;");
//        str = str.replace("\\","&#39;");
//        str = str.replace("\"","&quot;");
		return str;
	}

	/**
	 * 将html标签特殊字符还原
	 * @param str
	 * @return str
	 */
	public static String reverseReplaceHtmlCh(String str){
//		str = str.replace("&amp;","&");
//        str = str.replace("&lt;","<");
//        str = str.replace("&gt;",">");
//        str = str.replace("&#39;","\\");
//        str = str.replace("&quot;","\"");
		return str;
	}

	/**
	 * 去除字符串中除中间空格外的特殊字符
	 * @author jianghb
	 * @param str 需要处理的字符串
	 * @return 处理后返回的字符串
	 */
	public static String deleteWhitespace(String str)
	 {
	      if (StringUtil.isEmpty(str)) {
	        return str;
	      }else {
	    	  str = str.trim();
	      }

	      int sz = str.length();
	      char[] chs = new char[sz];
	      int count = 0;
	      for (int i = 0; i < sz; ++i) {
	      if (!(Character.isWhitespace(str.charAt(i))) || str.charAt(i) == 0x0020) {
	          chs[(count++)] = str.charAt(i);
	        }
	      }

	      if (count == sz) {
	       return str;
	      }
	      return new String(chs, 0, count);
	  }

	public static String formatPrice(Integer places, BigDecimal bigDecimal){
		if(bigDecimal == null){
			return "";
		}
		bigDecimal = bigDecimal.setScale(places, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.toString();
	}

	/**
	 * 默认设置null的字符串为空字符串
	 * @author jianghb
	 * @param str 传入的字符串
	 * @return 处理过后的字符串
	 */
	public static String defaultEmpty(String str){
		return (str == null) ? "" : str;
	}

	/**
	 * 是否是数字
	 * @param str
	 * @return
	 */
	public static Boolean isNumeric(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获得随机子母和数字的组合
	 *
	 * @param length
	 * @return 随机字母和数字的组合
	 */
	public static String getCharAndNum(int length) {
		String val = "";
		Random random = new Random();
		String charOrNum = "";
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				charOrNum = "char";
			} else if (i == (length - 1)) {
				charOrNum = "num";
			} else {
				charOrNum = (random.nextInt(2) % 2 == 0) ? "char" : "num";
			}
			if ("num".equalsIgnoreCase(charOrNum)) {// 如何获得 0-9之间的数字
				int num = random.nextInt(10);
				if (num == 0) {
					num = 2;
				}
				if (num == 1) {
					num = 5;
				}
				val += String.valueOf(num);
			} else {// 如何获得随机的字符 //97 - 122 0-25
				char char1 = (char) (97 + random.nextInt(26));
				if (char1 == 'o') {
					char1 = 'm';
				}
				val += String.valueOf(char1);
			}
		}
		val = val.toUpperCase();
		return val;
	}


	/**
	 * 去掉url中的路径，留下请求参数部分
	 * @param strURL url地址
	 * @return url请求参数部分
	 * @author jianghb
	 */
	private static String TruncateUrlPage(String strURL){
		String strAllParam=null;
		String[] arrSplit=null;
		strURL=strURL.trim().toLowerCase();
		arrSplit=strURL.split("[?]");
		if(strURL.length()>1){
			if(arrSplit.length>1){
				for (int i=1;i<arrSplit.length;i++){
					strAllParam = arrSplit[i];
				}
			}
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对
	 * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * @param URL  url地址
	 * @return  url请求参数部分
	 * @author jianghb
	 */
	public static Map<String, String> urlSplit(String URL){
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit=null;
		String strUrlParam=TruncateUrlPage(URL);
		if(strUrlParam==null){
			return mapRequest;
		}
		arrSplit=strUrlParam.split("[&]");
		for(String strSplit:arrSplit){
			String[] arrSplitEqual=null;
			arrSplitEqual= strSplit.split("[=]");
			//解析出键值
			if(arrSplitEqual.length>1){
				//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			}else{
				if(arrSplitEqual[0]!=""){
					//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
