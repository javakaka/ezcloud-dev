package com.ezcloud.framework.taglib.spi;

import com.ezcloud.framework.util.StringUtil;
import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;

/**
* 树结构工具类
* @author TongJianbo
*
*/
public class WebTreeHandler
{
	public void drawTree(DataSet elementDataSet, String bureau_no, StringBuffer treeStr)
	{
		drawTree(elementDataSet, bureau_no, treeStr, null, null, null, null, null);
	}

	public void drawTree(DataSet elementDataSet, String bureau_no, StringBuffer treeStr, Row treeCssRow)
	{
		drawTree(elementDataSet, bureau_no, treeStr, treeCssRow, null, null, null, null);
	}

	public void drawTree(DataSet elementDataSet, String bureau_no, StringBuffer treeStr, Row treeCssRow, String sHasScript)
	{
		drawTree(elementDataSet, bureau_no, treeStr, treeCssRow, sHasScript, null, null, null);
	}

	public void drawTree(DataSet elementDataSet, String bureau_no, StringBuffer treeStr, Row treeCssRow, String sHasScript, String sBorderStyle)
	{
		drawTree(elementDataSet, bureau_no, treeStr, treeCssRow, sHasScript, sBorderStyle, null, null);
	}

	/**
	* 
	* @param elementDataSet 树节点数据
	* @param bureau_no		区域编号
	* @param treeStr		输出用字串
	* @param treeCssRow		显示树时的节点图标
	* @param sHasScript		是否全部展开(1为不展开,0为展开)
	* @param sBorderStyle	CSS语法描述的节点边框样式
	* @param sVerticalBgPath	表示竖线的图
	* @param sHorizonalBgPath	表示横线的图
	*/
	@SuppressWarnings("unchecked")
	public void drawTree(DataSet elementDataSet, String bureau_no, StringBuffer treeStr, Row treeCssRow, 
	String sHasScript, String sBorderStyle, String sVerticalBgPath, String sHorizonalBgPath)
	{
		if ((sVerticalBgPath == null) || (sVerticalBgPath.equals("")))
		{
			sVerticalBgPath = "./images/dot.gif";
		}
		if ((sHorizonalBgPath == null) || (sHorizonalBgPath.equals("")))
		{
			sHorizonalBgPath = "./images/dot.gif";
		}
		if ((sBorderStyle == null) || (sBorderStyle.equals("")))
		{
			sBorderStyle = "border:1px solid #000000";
		}
		if ((sHasScript == null) || (sHasScript.equals("")))
		{
			sHasScript = "0";
		}
		String str1 = "<tr><td colspan='2' height='20' style='background-image:url(" + sVerticalBgPath + ");background-repeat:repeat-y;background-position:center top'></td></tr>";
		Row curRow = null;
		Row elementRow =null;
		String bureau_id;
		for (int i = 0; i < elementDataSet.size(); i++)
		{
			elementRow = (Row)elementDataSet.get(i);
			bureau_id = (String)elementRow.get("ID");
			if (bureau_id.equals(bureau_no))
			{
				curRow = (Row)elementDataSet.get(i);
				break;
			}
			if (i == elementDataSet.size())
			{
				return;
			}
		}
		String elementName ="";
		String type ="";
		if (curRow != null)
		{
			elementName = (String)curRow.get("NAME");
			type = (String)curRow.get("TYPE");
			String cssType = "type" + type;
			String imageHtml = "";
			String jsType = "";
			DataSet localDataSet = new DataSet();
			Object localObject2;
			String cur_up_id;
			//查找子节点
			for (int j = 0; j < elementDataSet.size(); j++)
			{
				localObject2 = (Row)elementDataSet.get(j);
				cur_up_id = (String)((Row)localObject2).get("UP_ID");
				if (cur_up_id.equals(bureau_no))
				{
					localDataSet.add(localObject2);
				}
			}
			if ((treeCssRow != null) && ((String)treeCssRow.get(cssType) != null))
			{
				imageHtml = "<img src='" + (String)treeCssRow.get(cssType) + "'>";
				if (type.equals("2"))
					imageHtml = imageHtml + "<br>";
				jsType = (String)treeCssRow.get("js" + type);
				if (jsType != null)
				{
					jsType = StringUtil.replace(jsType, "$ID", bureau_no.substring(1));
					elementName = "<a href=javascript:" + jsType + ">" + (String)elementName + "</a>";
				}
			}
			treeStr.append("<table width='100%' border='0' cellpadding='0' cellspacing='0'>\n");
			treeStr.append("<tr>\n");
			treeStr.append("<td align='center' valign='top'><table border='0' cellpadding='0' cellspacing='0'><tr><td>&nbsp;</td><td align='center' style='" + sBorderStyle + "'>" + imageHtml + elementName + "</td></tr>");
			if (!localDataSet.isEmpty())
			{
				treeStr.append(str1);
				if (sHasScript.equals("1"))
				{
					treeStr.append("<tr><td colspan='2' align='center'><img name='" + bureau_no + "pic' onclick='action(\"" + bureau_no + "\");' src='" + "/images/add.gif'></td></tr>");
				}
			}
			treeStr.append("</table></td>\n</tr>\n");
			if (!localDataSet.isEmpty())
			{
				treeStr.append("<tr><td align='center'>");
				if (sHasScript.equals("1"))
				{
					treeStr.append("<div name='" + bureau_no + "' id='" + bureau_no + "' style='display:none'>");
				}
				treeStr.append("<table border='0' cellpadding='0' cellspacing='0'>");

				if (localDataSet.size() > 1)
				{
					treeStr.append("<tr>\n");
					for (int j = 0; j < localDataSet.size(); j++)
					{
						localObject2 = "100%";
						cur_up_id = "center";
						if ((j == 0) || (j == localDataSet.size() - 1))
						{
							localObject2 = "50%";
						}
						if (j == 0)
						{
							cur_up_id = "right";
						}
						else if (j == localDataSet.size() - 1)
						{
							cur_up_id = "left";
						}
						treeStr.append("<td height='1' align='" + cur_up_id + "'><table border='0' cellpadding='0' cellspacing='0' height='1' width='" + (String)localObject2 + "'><tr><td style='background-image:url(" + sHorizonalBgPath + ");background-repeat:repeat-x;'></td></tr></table></td>\n");
					}
					treeStr.append("</tr>\n");
				}
				treeStr.append("<tr>\n");
				for (int j = 0; j < localDataSet.size(); j++)
				{
					localObject2 = (Row)localDataSet.get(j);
					cur_up_id = (String)((Row)localObject2).get("UP_ID");
					String str9 = (String)((Row)localObject2).get("ID");
					treeStr.append("<td align='center' valign='top'><table border='0' cellpadding='0' cellspacing='0'>" + str1 + "<tr><td valign='top'>\n");
					drawTree(elementDataSet, str9, treeStr, treeCssRow, sHasScript, sBorderStyle, sVerticalBgPath, sHorizonalBgPath);
					treeStr.append("</td></tr></table></td>\n");
				}
				treeStr.append("</tr>\n");
				treeStr.append("</table>");
				if (sHasScript.equals("1"))
				{
					treeStr.append("</div>\n");
				}
				treeStr.append("</td>\n");
			}
			treeStr.append("</tr>\n");
			treeStr.append("</table>\n");
		}
	}
}
