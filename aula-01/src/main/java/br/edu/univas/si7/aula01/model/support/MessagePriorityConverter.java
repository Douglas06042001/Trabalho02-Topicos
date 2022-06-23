package br.edu.univas.si7.aula01.model.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)

/**
 *  Converte de MessagePriority para Interio e vice-versa.
 *
 */
public class MessagePriorityConverter implements AttributeConverter<MessagePriority, Integer>{

	@Override
	public Integer convertToDatabaseColumn(MessagePriority attribute) {
		return attribute.getCode();
	}

	@Override
	public MessagePriority convertToEntityAttribute(Integer dbData) {
		return MessagePriority.getPriority(dbData);
	}

	
}
