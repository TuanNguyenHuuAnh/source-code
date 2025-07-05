package vn.com.unit.ep2p.dialect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.sf.amateras.mirage.DefaultEntityOperator;
import jp.sf.amateras.mirage.EntityCreationFailedException;
import jp.sf.amateras.mirage.EntityOperator;
import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.bean.BeanDesc;
import jp.sf.amateras.mirage.bean.MapBeanDescImpl;
import jp.sf.amateras.mirage.bean.MapPropertyDescImpl;
import jp.sf.amateras.mirage.bean.PropertyDesc;
import jp.sf.amateras.mirage.dialect.Dialect;
import jp.sf.amateras.mirage.naming.NameConverter;
import jp.sf.amateras.mirage.type.ValueType;
import jp.sf.amateras.mirage.util.MirageUtil;

public class JcanaryEntityOperator extends DefaultEntityOperator {

	private static final Logger logger = Logger.getLogger(JcanaryEntityOperator.class.getName());

	/**
	 * Creates and returns one entity instance from the ResultSet.
	 *
	 * @param <T> the type parameter of entity class
	 * @param entityType the entity class
	 * @param rs the ResultSet
	 * @param meta the ResultSetMetaData
	 * @param columnCount the column count
	 * @param beanDesc the BeanDesc of the entity class
	 * @param dialect the Dialect
	 * @param valueTypes the list of ValueTypes
	 * @param nameConverter the NameConverter
	 * @return the instance of entity class or Map
	 * @throws EntityCreationFailedException if {@link EntityOperator} failed to create a result entity
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public <T> T createEntity(Class<T> entityType, ResultSet rs,
			ResultSetMetaData meta, int columnCount, BeanDesc beanDesc,
			Dialect dialect, List<ValueType<?>> valueTypes, NameConverter nameConverter) {

		try {
			{
				ValueType valueType = MirageUtil.getValueType(entityType, null, dialect, valueTypes);
				if(valueType != null){
					return (T) ((ValueType<T>) valueType).get(entityType, rs, 1);
				}
			}

			T entity = null;

			if(entityType == Map.class){
				entity = (T) new HashMap<String, Object>();
			} else {
				Constructor<T>[] constructors = (Constructor<T>[]) entityType.getDeclaredConstructors();
				for(Constructor<T> constructor: constructors){
					try {
						constructor.setAccessible(true);
						Class<?>[] types = constructor.getParameterTypes();
						Object[] params = new Object[types.length];
						for(int i = 0; i < params.length; i++){
							ValueType valueType = MirageUtil.getValueType(types[i], null, dialect, valueTypes);
							if(valueType != null){
								params[i] = valueType.getDefaultValue();
							}
						}
						entity = constructor.newInstance(params);
					} catch (InstantiationException e) {
						// ignore
					} catch (IllegalAccessException e) {
						// ignore
					} catch (IllegalArgumentException e) {
						// ignore
					} catch (InvocationTargetException e) {
						// ignore
					}
				}
			}
			if(entity == null) {
				throw new EntityCreationFailedException();
			}

			for(int i = 0; i < columnCount; i++){
				String columnLabel = meta.getColumnLabel(i + 1);
				String columnName = meta.getColumnName(i + 1);
				PropertyDesc pd = null;

				for(int j = 0; j < beanDesc.getPropertyDescSize(); j++){
					PropertyDesc property = beanDesc.getPropertyDesc(j);
					Column column = property.getAnnotation(Column.class);
					if(column != null && columnLabel.equalsIgnoreCase(column.name())){
						pd = property;
						break;
					}
				}
				
				if(pd == null){
					for(int j = 0; j < beanDesc.getPropertyDescSize(); j++){
						PropertyDesc property = beanDesc.getPropertyDesc(j);
						Column column = property.getAnnotation(Column.class);
						if(column != null && columnName.equalsIgnoreCase(column.name())){
							pd = property;
							break;
						}
					}
				}

				if(pd == null){
					String propertyName = nameConverter.columnToProperty(columnLabel);
					pd = beanDesc.getPropertyDesc(propertyName);
					
					// SQLServerResultSetMetaData
					int columnType = meta.getColumnType(i + 1);
					if( beanDesc instanceof MapBeanDescImpl ) {
						pd = this.createMapPropertyDesc(propertyName, columnType);
					}
				}

				if(pd == null){
					String propertyName = nameConverter.columnToProperty(columnName);
					pd = beanDesc.getPropertyDesc(propertyName);
				}

				if(pd != null){
					Class<?> propertyType = pd.getPropertyType();
					ValueType valueType = MirageUtil.getValueType(propertyType, pd, dialect, valueTypes);
					if(valueType != null){
						pd.setValue(entity, valueType.get(propertyType, rs, columnLabel));
					} else {
						if (logger.isLoggable(Level.FINE)) {
							logger.fine(String.format("column [%s] is ignored because property [%s]'s type is not supported: %s",
									columnLabel, pd.getPropertyName(), propertyType.getName()));
						}
					}
				} else {
					if (logger.isLoggable(Level.FINER)) {
						logger.finer(String.format("column [%s] is ignored because property is not found in the bean",
								columnLabel));
					}
				}
			}

			return entity;
		} catch (SQLException e) {
			throw new EntityCreationFailedException(e);

		} catch (SecurityException e) {
			throw new EntityCreationFailedException(e);

		} catch (IllegalArgumentException e) {
			throw new EntityCreationFailedException(e);

		}
	}
	
	/**
	 * Create MapPropertyDesc
	 * 
	 * @param propertyName
	 * 			type String
	 * @param columnType
	 * 			type int
	 * @return PropertyDesc
	 * @author KhoaNA
	 */
	private PropertyDesc createMapPropertyDesc(String propertyName, int columnType) {
		PropertyDesc result = null;
		
		if( Types.DATE == columnType || Types.TIMESTAMP == columnType ) {
			result = new MapPropertyDescImpl(propertyName, new Date());
		} else if( Types.NUMERIC == columnType ) {
			result = new MapPropertyDescImpl(propertyName, new BigDecimal(0));
		} else if( Types.INTEGER == columnType ) {
			result = new MapPropertyDescImpl(propertyName, new Integer(0));
		}
		
		return result;
	}
}
