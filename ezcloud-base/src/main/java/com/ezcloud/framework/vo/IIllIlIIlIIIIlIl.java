 package com.ezcloud.framework.vo;
 
 import java.util.Comparator;
 /**
  * 通用比较器
  * @author Tong
  */
@SuppressWarnings("rawtypes")
final class IIllIlIIlIIIIlIl implements Comparator
 {
   public int compare(Object obj1, Object obj2)
   {
     Row oRow1 = (Row)obj1;
     Row oRow2 = (Row)obj2;
    int i = new Integer(String.valueOf(oRow1.get("COUNT"))).intValue();
    int j = new Integer(String.valueOf(oRow2.get("COUNT"))).intValue();
    return i - j;
   }
 }

