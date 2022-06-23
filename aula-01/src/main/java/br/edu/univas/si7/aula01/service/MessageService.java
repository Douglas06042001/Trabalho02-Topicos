package br.edu.univas.si7.aula01.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.univas.si7.aula01.controller.exception.InvalidDataException;
import br.edu.univas.si7.aula01.controller.exception.ObjectNotFoundException;
import br.edu.univas.si7.aula01.dto.MessageDTO;
import br.edu.univas.si7.aula01.model.Message;
import br.edu.univas.si7.aula01.model.support.MessagePriority;
import br.edu.univas.si7.aula01.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository msgRepo;
	
	public List<MessageDTO> getAllMessages() {
		List<Message> listMSG = msgRepo.findAll();
		
//		List<MessageDTO> listDTO = new ArrayList<MessageDTO>();
//		for (Message msg : listMSG) {
//			MessageDTO dto = new MessageDTO(msg);
//			listDTO.add(dto);
//		}
//		return listDTO;
		
		//outra opção no lugar do loop, usando Stream do Java 8
		return listMSG.stream()
				.map(msg -> new MessageDTO(msg))
				.collect(Collectors.toList());
	}
	
	public Message getMessageById(@PathVariable Integer id) {
		if(id == null) {
			throw new InvalidDataException("id não pode ser null.");
		}
		Optional<Message> obj = msgRepo.findById(id);
		
//		Message msg = obj.orElseThrow(() -> new ObjectNotFoundException("Mensagem não encontrada: " + id));
//		MessageDTO dto = new MessageDTO(msg);
//		return dto;
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Mensagem não encontrada: " + id));
	}
	
	public void createMessage(MessageDTO dto) {
		Message msg = toMessage(dto);
		msgRepo.save(msg);
	}

	public void updateMessage(MessageDTO dto, Integer id) {
		if(dto == null) {
			throw new InvalidDataException("Mensagem não pode ser null.");
		}
		Message msg = getMessageById(id);
		updateData(dto, msg);
		msgRepo.save(msg);
	}

	public void deleteMessage(Integer id) {
		Message msg = getMessageById(id);
		msgRepo.delete(msg);
	}

	private void updateData(MessageDTO dto, Message msg) {
		msg.setMessage(dto.getMessage());
		msg.setPriority(MessagePriority.getPriority(dto.getPriority()));
	}
	
	private Message toMessage(MessageDTO dto) {
		Message msg = new Message();
		msg.setMessage(dto.getMessage());
		msg.setPriority(MessagePriority.getPriority(dto.getPriority()));
		return msg;
	}

}
