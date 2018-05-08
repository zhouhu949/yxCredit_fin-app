package com.zw.baoFu.base.response;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zw.baoFu.util.TransConstant;

@XStreamAlias("trans_content")
public class TransContent<T> {
	
	
	@XStreamAlias("trans_head")
	private TransHead trans_head;
	
	@XStreamAlias("trans_reqDatas")
	private TransReqDatas<T> trans_reqDatas;
	
	
	@XStreamOmitField
	private XStream xStream;
	
	@XStreamOmitField
	private String data_type;
	
	public TransContent(){}

	/**
	 * 数据类型 xml/json
	 * @param data_type
	 */
	public TransContent(String data_type) {
		if (TransConstant.data_type_json.equals(data_type)) {
			xStream = new XStream(new JettisonMappedXmlDriver());
		}else{
			xStream = new XStream(new DomDriver("UTF-8", new NoNameCoder())) ;
		}
		// 启用Annotation
		xStream.autodetectAnnotations(true);
		this.data_type = data_type;
	}

	public TransHead getTrans_head() {
		return trans_head;
	}

	public void setTrans_head(TransHead trans_head) {
		this.trans_head = trans_head;
	}

	public TransReqDatas<T> getTrans_reqDatas() {
		return trans_reqDatas;
	}

	public void setTrans_reqDatas(TransReqDatas<T> trans_reqDatas) {
		this.trans_reqDatas = trans_reqDatas;
	}
	
	
	public String obj2Str(Object obj) {
		return xStream.toXML(obj);
	}

	public Object str2Obj(String str,Class<T> clazz) {
		xStream.alias("trans_content", this.getClass());
		xStream.alias("trans_reqData", clazz);
		return xStream.fromXML(str);
	}
}