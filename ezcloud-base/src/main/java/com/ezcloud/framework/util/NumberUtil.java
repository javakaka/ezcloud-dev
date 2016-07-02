package com.ezcloud.framework.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtil {
	public static String convertToChineseNum(String paramString) {
		String str1 = "零壹贰叁肆伍陆柒捌玖";
		String str2 = "分角元拾佰仟万拾佰仟亿拾佰仟万";
		float f = 0.0F;
		String str3 = "";
		paramString = StringUtil.replace(paramString, ",", "");
		try {
			f = Float.parseFloat(paramString);
		} catch (Exception localException) {
			return "传入参数非数字！";
		}
		if (f > 9999999999999.9902D) {
			return "超出范围的人民币值";
		}
		if (paramString.indexOf(".") != -1) {
		String	str4 = paramString.substring(0, paramString.indexOf("."));
			String str5;
			if ((str5 = paramString.substring(paramString.indexOf(".") + 1))
					.length() == 1)
				str4 = str4 + str5 + "0";
			else
				str4 = str4 + str5.substring(0, 2);
			paramString = str4;
		} else {
			paramString = paramString + "00";
		}
		String str4 = paramString;
		int i = paramString.length();
		int j = 0;
		while (j < i) {
			int k = Integer.parseInt(str4.substring(j, j + 1));
			String str6 = str1.substring(k, k + 1);

			String str7 = str2.substring(i - j - 1, i - j);
			if (!str6.equals( "零" )) {
				str3 = str3 + str6 + str7;
			} else {
				if (!str7.equals( "亿" ) && (!str7.equals( "万" )) && (!str7.equals( "元" ))) {
					if (!str7.equals( "零" ))
						;
				} else {
					while (str3.endsWith("零")) {
						str3 = str3.substring(0, str3.length() - 1);
					}
				}
				if (str7.equals( "亿" ) || ((str7.equals( "万" )) && (!str3.endsWith("亿")))
						|| str7.equals( "元" )) {
					str3 = str3 + str7;
				} else {
					boolean bool1 = str3.endsWith("亿");
					boolean bool2 = str3.endsWith("零");
					if (str3.length() > 1) {
						boolean bool3 = str3.substring(str3.length() - 2,
								str3.length()).startsWith("零");
						if ((!bool2) && ((bool3) || (!bool1))) {
							str3 = str3 + str6;
						}

					} else if ((!bool2) && (!bool1)) {
						str3 = str3 + str6;
					}
				}
			}
			j++;
		}

		while (str3.endsWith("零")) {
			str3 = str3.substring(0, str3.length() - 1);
		}

		while (str3.endsWith("元")) {
			str3 = str3 + "整";
		}

		return str3;
	}

	public String getCurrency(String paramString) {
		NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance();

		String str = null;
		try {
			double d = Double.parseDouble(paramString);
			str = localNumberFormat.format(d);
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		return str;
	}

	public static int getGBD(int paramInt1, int paramInt2) {
		int i = 0;
		int j = 0;
		int k = 0;
		if (paramInt1 < paramInt2) {
			i = paramInt1;
			paramInt1 = paramInt2;
			paramInt2 = i;
		}
		j = paramInt1;
		k = paramInt2;
		int m = 0;
		do {
			m = j % k;
			j = k;
			k = m;
		} while (k != 0);

		return j;
	}

	public static String getNumWithoutEndZero(double paramDouble) {
		String str;
		if ((str = String.valueOf(paramDouble)).indexOf(".") != -1) {
			while (str.lastIndexOf("0") == str.length() - 1) {
				str = str.substring(0, str.length() - 1);
			}
			if (str.indexOf(".") == str.length() - 1) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}

	public static int getRandomNum(int paramInt1, int paramInt2) {
		int i = 0;
		try {
			if (paramInt1 > paramInt2) {
				return (i = new Random().nextInt(paramInt1 - paramInt2))
						+ paramInt2;
			}

			return (i = new Random().nextInt(paramInt2 - paramInt1))
					+ paramInt1;
		} catch (Exception localException2) {
			localException2.printStackTrace();
		}
		return i + paramInt1;
	}

	public static String getStorageSize(double paramDouble) {
		if (paramDouble == 0.0D) {
			return "0B";
		}

		if (paramDouble < 1024.0D) {
			return paramDouble + "B";
		}

		if (paramDouble < 1048576.0D) {
			return round(paramDouble / 1000.0D, -1) + "K";
		}

		if (paramDouble < 1073741824.0D) {
			return round(paramDouble / 1000000.0D, -1) + "M";
		}

		return round(paramDouble / 1000000000.0D, -1) + "G";
	}

	public static double round(double paramDouble, int paramInt) {
		BigDecimal localBigDecimal = (localBigDecimal = new BigDecimal(
				paramDouble)).movePointLeft(paramInt);

		double d = (localBigDecimal = (localBigDecimal = new BigDecimal(
				Math.round(localBigDecimal.doubleValue())))
				.movePointRight(paramInt)).doubleValue();
		localBigDecimal = null;
		return d;
	}
}