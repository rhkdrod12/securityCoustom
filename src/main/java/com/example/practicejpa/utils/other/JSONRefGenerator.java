package com.example.practicejpa.utils.other;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.voodoodyne.jackson.jsog.JSOGRef;

import java.util.UUID;

/**
 * Id부여시 UUID를 사용하여 JSON을 만들기 위한 클래스
 * JSOG는 int형이라 부족함!!
 */
public class JSONRefGenerator extends ObjectIdGenerator<JSOGRef> {
	
	private static final long serialVersionUID = 1L;
	protected transient String _nextValue;
	protected final Class<?> _scope;
	
	public JSONRefGenerator() {
		this(null, null);
	}
	
	public JSONRefGenerator(Class<?> scope, String nextValue) {
		this._scope = scope;
		this._nextValue = nextValue;
	}
	
	public Class<?> getScope() {
		return this._scope;
	}
	
	public boolean canUseFor(ObjectIdGenerator<?> gen) {
		return gen.getClass() == this.getClass() && gen.getScope() == this._scope;
	}
	
	public ObjectIdGenerator<JSOGRef> forScope(Class<?> scope) {
		return this._scope == scope ? this : new JSONRefGenerator(scope, this._nextValue);
	}
	
	public ObjectIdGenerator<JSOGRef> newForSerialization(Object context) {
		return new JSONRefGenerator(this._scope, UUID.randomUUID().toString());
	}
	
	public IdKey key(Object key) {
		return new IdKey(this.getClass(), this._scope, key);
	}
	
	public JSOGRef generateId(Object forPojo) {
		String id = UUID.randomUUID().toString();
		return new JSOGRef(id);
	}
	
	public boolean maySerializeAsObject() {
		return true;
	}
	
	public boolean isValidReferencePropertyName(String name, Object parser) {
		return "@ref".equals(name);
	}
}
