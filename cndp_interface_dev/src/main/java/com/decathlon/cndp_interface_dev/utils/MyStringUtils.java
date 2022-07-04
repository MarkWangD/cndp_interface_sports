package com.decathlon.cndp_interface_dev.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MyStringUtils {
	public static String CONTENT_TYPE_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	public static String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

	public static void main(String[] args) {
		String temp = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><POSLog MajorVersion=\"6\" MinorVersion=\"0\" FixVersion=\"0\" xsi:schemaLocation=\"http://www.nrf-arts.org/IXRetail/v6.0.0/poslog ../POSLogV6.0.0.xsd\" xmlns=\"http://www.nrf-arts.org/IXRetail/v6.0.0/poslog\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Transaction TypeCode=\"SaleTransaction\"><BusinessUnit><UnitID Name=\"decathlon.com.hk\" TypeCode=\"WebSite\">50-191-191</UnitID></BusinessUnit><BusinessUnit><UnitID Name=\"HK\" TypeCode=\"DKT:Country\">15-67-67</UnitID></BusinessUnit><Channel OnLineOffLineTypeCode=\"OnLine\"><ChannelID Description=\"WebSite\"/><TouchPointID ManagedTypeCode=\"Retailer\" PhysicalDigitalCode=\"Digital\" Name=\"Desktop\"/></Channel><WorkstationID TypeCode=\"Website\" SerialNumber=\"CUBE-ashkpr\">67</WorkstationID><SequenceNumber>29735669</SequenceNumber><TransactionID>50-191-191-20181225012759-67-29735669</TransactionID><POSLogDateTime TypeCode=\"Transaction\">2018-12-25T01:27:59+08:00</POSLogDateTime><POSLogDateTime TypeCode=\"Message\">2018-12-27T13:11:39+08:00</POSLogDateTime><CurrencyCode>HKD</CurrencyCode><InvoiceNumber DateTime=\"2018-12-26T14:04:19+08:00\">hk29735669</InvoiceNumber><RetailTransaction TransactionStatus=\"Shipped\"><SpecialOrderNumber>hk29735669</SpecialOrderNumber><LineItem VoidFlag=\"false\" EntryMethod=\"Automatic\" CancelFlag=\"false\" DeleteFlag=\"false\"><Sale ItemType=\"Stock\" NotNormallyStockedFlag=\"false\" TaxableFlag=\"true\" NonDiscountableFlag=\"false\"><ItemID>2358614</ItemID><SpecialOrderNumber>hk29735669</SpecialOrderNumber><MerchandiseHierarchy Level=\"Category\" ID=\"DKT:PassionBrand\">1</MerchandiseHierarchy><Description TypeCode=\"Web\">500藍綠色滑雪袋</Description><UnitCostPrice>64.77</UnitCostPrice><RegularSalesUnitPrice>129.00</RegularSalesUnitPrice><ExtendedAmount>129.00</ExtendedAmount><ExtendedDiscountAmount>0.00</ExtendedDiscountAmount><Quantity>1</Quantity><Tax TaxType=\"VAT\" TypeCode=\"Sale\"><SequenceNumber>1</SequenceNumber><TaxableAmount TaxIncludedInTaxableAmountFlag=\"false\">129.00</TaxableAmount><Amount>0.00</Amount><Percent>0.00</Percent><ReasonCode Description=\"VAT 0.00%\" Name=\"DKT:HighVATRate\"/><TaxGroupID>1</TaxGroupID></Tax><TaxIncludedInPriceFlag>true</TaxIncludedInPriceFlag></Sale><SequenceNumber>1</SequenceNumber><DateTime TypeCode=\"Transaction\">2018-12-25T01:27:59+08:00</DateTime></LineItem><LineItem VoidFlag=\"false\" EntryMethod=\"Automatic\" CancelFlag=\"false\" DeleteFlag=\"false\"><Sale ItemType=\"Service\" NotNormallyStockedFlag=\"true\" TaxableFlag=\"true\" NonDiscountableFlag=\"false\"><ItemID>2102120</ItemID><SpecialOrderNumber>hk29735669</SpecialOrderNumber><MerchandiseHierarchy Level=\"Category\" ID=\"DKT:Unknown\">0</MerchandiseHierarchy><Description TypeCode=\"Web\">運費</Description><UnitCostPrice>0.00</UnitCostPrice><RegularSalesUnitPrice>0.00</RegularSalesUnitPrice><ExtendedAmount>0.00</ExtendedAmount><ExtendedDiscountAmount>0.00</ExtendedDiscountAmount><Quantity>1</Quantity><Tax TaxType=\"VAT\" TypeCode=\"Sale\"><SequenceNumber>1</SequenceNumber><TaxableAmount TaxIncludedInTaxableAmountFlag=\"false\">0.00</TaxableAmount><Amount>0.00</Amount><Percent>0.00</Percent><ReasonCode Description=\"VAT 0.00%\" Name=\"DKT:HighVATRate\"/><TaxGroupID>1</TaxGroupID></Tax><TaxIncludedInPriceFlag>true</TaxIncludedInPriceFlag></Sale><SequenceNumber>2</SequenceNumber><DateTime TypeCode=\"Transaction\">2018-12-25T01:27:59+08:00</DateTime></LineItem><LineItem><SaleForDelivery ItemType=\"DKT:ShippingGroup\" ItemSubType=\"DKT:RegularShipment\"><ItemID>sghk30223068</ItemID><SpecialOrderNumber>hk29735669</SpecialOrderNumber><Description>Order hk29735669 - Shipment of 12/26/2018 </Description><Item ItemType=\"Stock\"><ItemID>2358614</ItemID><UnitCostPrice>61.83</UnitCostPrice><ExtendedAmount>129.00</ExtendedAmount><ExtendedDiscountAmount>0.00</ExtendedDiscountAmount><Quantity>1</Quantity><Tax TaxType=\"VAT\" TypeCode=\"Sale\"><SequenceNumber>1</SequenceNumber><TaxableAmount TaxIncludedInTaxableAmountFlag=\"false\">129.00</TaxableAmount><Amount>0.00</Amount><Percent>0.00</Percent><ReasonCode Description=\"VAT 0.00%\" Name=\"DKT:HighVATRate\"/><TaxGroupID>1</TaxGroupID></Tax><SerialNumber Name=\"DKT:SupplyOrderNumber\">0000031686</SerialNumber><ItemLink>1</ItemLink></Item><Item ItemType=\"Service\"><ItemID>2102120</ItemID><ExtendedAmount>0.00</ExtendedAmount><ExtendedDiscountAmount>0.00</ExtendedDiscountAmount><Quantity>1</Quantity><Tax TaxType=\"VAT\" TypeCode=\"Sale\"><SequenceNumber>1</SequenceNumber><TaxableAmount TaxIncludedInTaxableAmountFlag=\"false\">0.00</TaxableAmount><Amount>0.00</Amount><Percent>0.00</Percent><ReasonCode Description=\"VAT 0.00%\" Name=\"DKT:HighVATRate\"/><TaxGroupID>1</TaxGroupID></Tax><SerialNumber Name=\"DKT:SupplyOrderNumber\">0000031686</SerialNumber><ItemLink>2</ItemLink></Item><Ticket EventID=\"sghk30223068\" EventName=\"InvoiceCreation\" EventDateTime=\"2018-12-26T14:04:19+08:00\"><SerialNumber Name=\"InvoiceNumber\">11800150000020292</SerialNumber></Ticket><Delivery Gender=\"Male\"><Name><Name TypeCode=\"FamilyName\">LUNG</Name><Name TypeCode=\"GivenName\">Shiu kai</Name></Name><Address AddressType=\"RetailStore\"><AddressLine RelativeOrder=\"1\">Mong Kok Grand Plaza Basement</AddressLine><AddressLine RelativeOrder=\"2\">Kowloon</AddressLine><City>Hongkong</City><PostalCode>999077</PostalCode><Country>HK</Country></Address><TelephoneNumber><LocalNumber>97821802</LocalNumber><ITUCountryCode>HK</ITUCountryCode></TelephoneNumber><EMail><EMailAddress>geigerlung@hotmail.com</EMailAddress></EMail><DueDate>2018-12-28</DueDate><ActualShipDateTime>2018-12-26T14:04:19+08:00</ActualShipDateTime><Method>smo_sfe_standard_clickandcollect</Method><TrackingNumber>252429952019</TrackingNumber><Courier>SFE</Courier><ShippingFee Currency=\"HKD\">0.00</ShippingFee><PreferredLocation><EstimatedDateTime>2018-12-28T03:28:00+08:00</EstimatedDateTime></PreferredLocation></Delivery><InventoryLocation><BusinessUnit Name=\"Guangzhou\" TypeCode=\"DistributionCenter\">9-97-97</BusinessUnit><BusinessUnit Name=\"Mong Kok\" TypeCode=\"DKT:EconomicUnit\">7-1618-1618</BusinessUnit><BusinessUnit Name=\"Mong Kok\" TypeCode=\"DKT:FiscalUnit\">7-1618-1618</BusinessUnit><BusinessUnit Name=\"Guangzhou\" TypeCode=\"DKT:StockOrigin\">9-97-97</BusinessUnit><BusinessUnit Name=\"Mong Kok\" TypeCode=\"DKT:ClickAndCollect\">7-1618-1618</BusinessUnit><ExactLocation Level=\"DKT:TurnoverType\">DKT:ClickAndCollect</ExactLocation><ExactLocation Level=\"DKT:TurnoverSubType\">DKT:ClickAndCollect</ExactLocation></InventoryLocation></SaleForDelivery><SequenceNumber>3</SequenceNumber><DateTime TypeCode=\"Transaction\">2018-12-25T01:27:59+08:00</DateTime><DateTime TypeCode=\"DKT:Shipped\">2018-12-26T14:04:19+08:00</DateTime><DateTime TypeCode=\"DKT:ReadyForPickUp\">2018-12-27T13:10:51+08:00</DateTime><DateTime TypeCode=\"DKT:StockShortage\">2018-12-26T08:58:19+08:00</DateTime><DateTime TypeCode=\"DKT:ReadyForPicking\">2018-12-25T02:17:59+08:00</DateTime></LineItem><LineItem><Tax TaxType=\"VAT\" TypeCode=\"Sale\"><SequenceNumber>1</SequenceNumber><TaxableAmount TaxIncludedInTaxableAmountFlag=\"false\">129.00</TaxableAmount><Amount>0.00</Amount><Percent>0.00</Percent><ReasonCode Description=\"VAT 0.00%\" Name=\"DKT:HighVATRate\"/><TaxGroupID>1</TaxGroupID></Tax><SequenceNumber>4</SequenceNumber><DateTime TypeCode=\"Transaction\">2018-12-25T01:27:59+08:00</DateTime></LineItem><LineItem><Tender TenderType=\"DKT:Paypal\" TypeCode=\"Sale\"><Amount Currency=\"HKD\">129.00</Amount><ReasonCode Description=\"PayPal\" Name=\"TenderID\">185</ReasonCode><TenderID TenderType=\"DKT:Paypal\">551687</TenderID></Tender><SequenceNumber>5</SequenceNumber><DateTime TypeCode=\"Transaction\">2018-12-25T01:28:00+08:00</DateTime><DateTime TypeCode=\"DKT:Authorized\">2018-12-25T01:33:00+08:00</DateTime><DateTime TypeCode=\"DKT:CaptureOK\">2018-12-25T01:48:19+08:00</DateTime></LineItem><Total TotalType=\"TransactionGrandAmount\" TypeCode=\"Sale\">129.00</Total><Total TotalType=\"TransactionNetAmount\" TypeCode=\"Sale\">129.00</Total><Total TotalType=\"TransactionTaxAmount\" TypeCode=\"Sale\">0.00</Total><Total TotalType=\"DiscountAmount\" TypeCode=\"Sale\">0.00</Total><Customer BusinessCustomerFlag=\"false\" Gender=\"Male\"><CustomerID>2091208701064</CustomerID><Name><Name TypeCode=\"FamilyName\">LUNG</Name><Name TypeCode=\"GivenName\">Shiu kai</Name></Name><Address AddressType=\"Billing\"><AddressLine RelativeOrder=\"1\">廣福邨廣𧙗樓1920室</AddressLine><AddressLine RelativeOrder=\"2\">大埔</AddressLine><City>新界區</City><PostalCode>999077</PostalCode><Country>香港</Country></Address><TelephoneNumber><LocalNumber>97821802</LocalNumber><ITUCountryCode>HK</ITUCountryCode></TelephoneNumber><EMail><EMailAddress>geigerlung@hotmail.com</EMailAddress></EMail><AffiliatedOrganization Name=\"Mong Kok\" TypeCode=\"RetailStore\">7-1618-1618</AffiliatedOrganization><Locale>zh_HK</Locale></Customer><LoyaltyAccount><CustomerID>2091208701064</CustomerID></LoyaltyAccount></RetailTransaction></Transaction></POSLog>";
		String replaceUnicode = stripNonValidXMLCharacters(temp);
		System.out.println(replaceUnicode);
	}

	// java中编码：URLEncoder.encode(strUri, "UTF-8");
	// java中解码：URLDecoder.decode(strUri, "UTF-8");

	/**
	 * 
	 * @param str
	 *            需要过滤的字符串
	 * @return
	 * @Description:过滤数字以外的字符
	 */
	public static String filterUnNumber(String str) {
		if(StringUtils.isNotEmpty(str)){
			// 只允数字
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
			return m.replaceAll("").trim();
		}else {
			return null;
		}
	}

	/**
	 * This method ensures that the output String has only valid XML unicode
	 * characters as specified by the XML 1.0 standard. For reference, please see
	 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
	 * standard</a>. This method will return an empty String if the input is null or
	 * empty.
	 *
	 * @param in
	 *            The String whose non-valid characters we want to remove.
	 * @return The in String, stripped of non-valid characters.
	 */
	public static String stripNonValidXMLCharacters(String in) {
		StringBuilder out = new StringBuilder(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in)))
			return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	public static String getTaskId(String tempFlag) {
		String currentTimeMillis = System.currentTimeMillis() + "";

		String taskId = tempFlag + currentTimeMillis.substring(currentTimeMillis.length() - 8);
		if (taskId.length() > 10) {
			taskId = taskId.substring(0, 10);
		} else if (taskId.length() < 10) {
			int tempInt = 10 - taskId.length();
			if (tempInt > 0) {
				for (int i = 0; i < tempInt; i++) {
					taskId = taskId + "0";
				}
			}
		}
		return taskId;
	}

	public static String createKeyCode() {
		StringBuilder sb = new StringBuilder();
		Date dt = new Date();
		sb.append(Math.abs((int) dt.getTime() % 3));

		sb.append(System.currentTimeMillis());
		long t = System.nanoTime();
		sb.append((t + "").replace(t / 1000000L + "", ""));
		sb.append((Math.random() + "").substring(2, 7));
		return sb.toString();
	}

	/**
	 * 检测字符串是否为 number 类型的数字
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		String strF = str.replaceAll("-", "");
		String strFormat = strF.replaceAll("\\.", "");
		if ("".equals(strFormat)) {
			return false;
		}
		for (int i = strFormat.length(); --i >= 0;) {
			if (!Character.isDigit(strFormat.charAt(i))) {
				return false;
			}
		}
		return true;

	}

	public static String getRandomNum(int length) {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}

	public static boolean isEmail(String email) {
		String emailPattern = "^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(emailPattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isMobile(String mobile) {
		String emailPattern = "^[\\d]{11}$";
		Pattern p = Pattern.compile(emailPattern);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static String num2Str(int num) {
		String str = "";
		if (num > 0) {
			if (num < 10000) {
				str = "+" + String.valueOf(num);
			} else if (num < 100000000) {
				str = "+" + String.valueOf((int) Math.floor(num / 10000.0D)) + "W";
			} else {
				str = "...";
			}
		}
		return str;
	}

	public static String num2Strj(int num) {
		String str = "";
		if (num > 0) {
			if (num < 10000) {
				str = String.valueOf(num);
			} else if (num < 100000000) {
				str = String.valueOf((int) Math.floor(num / 10000.0D)) + "W";
			} else {
				str = "...";
			}
		}
		return str;
	}

	public static String conversionString(HttpServletRequest request, String originalStr) {
		return conversionString(originalStr);
	}

	public static String conversionString(String originalStr) {
		String resultStr = originalStr;
		try {
			resultStr = originalStr.replaceAll(" ", "+");
		} catch (Exception localException) {
		}
		return resultStr;
	}

	public static String accuracy(double num, double total) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

		df.setMaximumFractionDigits(0);

		df.setRoundingMode(RoundingMode.HALF_UP);
		if ((total == 0.0D) && (num != 0.0D)) {
			return "100%";
		}
		if ((total == 0.0D) && (num == 0.0D)) {
			return "0%";
		}
		double accuracy_num = num / total * 100.0D;

		Integer strbfb = Integer.valueOf(df.format(accuracy_num));
		if (strbfb.intValue() > 100) {
			return "100%";
		}
		return strbfb + "%";
	}

	public static String accuracyFalse(double num, double total) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

		df.setMaximumFractionDigits(0);

		df.setRoundingMode(RoundingMode.HALF_UP);
		if ((total == 0.0D) && (num != 0.0D)) {
			return "100";
		}
		if ((total == 0.0D) && (num == 0.0D)) {
			return "0";
		}
		double accuracy_num = num / total * 100.0D;

		Integer strbfb = Integer.valueOf(df.format(accuracy_num));
		if (strbfb.intValue() > 100) {
			return "100";
		}
		return strbfb.toString();
	}

	public static String strReplaceAll(String str) {
		return str.trim().replaceAll(" ", "+").replaceAll("%2B", "+");
	}

	public static String filterEmojiStr(String str) {
		String result = str;
		if ((str != null) && (str.trim().length() > 0)) {
			Pattern emoji = Pattern.compile("[?-?]|[?-?]|[?-?]|[?]", 66);

			Matcher m = emoji.matcher(str);
			result = m.replaceAll("").trim();
			result = result.replaceAll("[?-?]|[?-?]|[?-?]", "");
		}
		return result;
	}

	public static boolean isEmpty(String str) {
		if ((str == null) || (str.trim().isEmpty())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static <E> String splitJoint(Collection<E> list) {
		return splitJoint(list, ",");
	}

	public static <E> String splitJoint(Collection<E> list, String regex) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for (E e : list) {
			if (e != null) {
				if (sb.length() == 0) {
					sb.append(e);
				} else {
					sb.append(regex).append(e.toString());
				}
			}
		}
		return sb.toString();
	}

	public static String GBKtoUTF8(String gbkStr) {
		if ((gbkStr == null) || (gbkStr.isEmpty())) {
			return gbkStr;
		}
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if ((m < 128) && (m >= 0)) {
				utfBytes[(k++)] = ((byte) m);
			} else {
				utfBytes[(k++)] = ((byte) (0xE0 | m >> 12));
				utfBytes[(k++)] = ((byte) (0x80 | m >> 6 & 0x3F));
				utfBytes[(k++)] = ((byte) (0x80 | m & 0x3F));
			}
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return new String(tmp);
		}
		return new String(utfBytes);
	}

	public static String getIndex(String str, int index) {
		if ((str == null) || (str.isEmpty())) {
			return null;
		}
		String[] ss = str.split(",");
		if (ss.length <= index) {
			return null;
		}
		return ss[index];
	}

	public static String removeHtmlTag(String inputString) {
		if (inputString == null) {
			return null;
		}
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

			String regEx_html = "<[^>]+>";

			String regEx_special = "\\&[a-zA-Z]{1,10};";

			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");
			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");
			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");
			Pattern p_special = Pattern.compile(regEx_special, 2);
			Matcher m_special = p_special.matcher(htmlStr);
			htmlStr = m_special.replaceAll("");
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;
	}

	/**
	 * 判断是否存在特殊字符串 (主要用来处理微信小程序特殊字符)
	 */
	public static boolean hasEmoji(String content) {
		Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 替换字符串中的emoji字符(主要用来处理微信小程序特殊字符)
	 */
	public static String replaceEmoji(String str) {
		if (!hasEmoji(str)) {
			return str;
		} else {
			str = str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", " ");
			return str;
		}
	}
}
