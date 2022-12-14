package br.com.gg.ecommerce;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonDeserializer<T> implements Deserializer<T>{

	public static final String TYPE_CONFIG = "br.com.gg.ecommerce.type_config";
	
	private final Gson gson = new GsonBuilder().create();
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String typeName = String.valueOf(configs.get(TYPE_CONFIG));
		try {
			this.type = (Class<T>) Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Tipo de deseriliza??o n?o existe no caminho", e);
		}
	}

	@Override
	public T deserialize(String s, byte[] bytes) {
		return gson.fromJson(new String(bytes), type);
	}
}
