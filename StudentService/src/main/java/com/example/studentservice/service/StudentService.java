package com.example.studentservice.service;

import com.example.studentservice.entity.Student;
import com.example.studentservice.feignClients.AddressFeignClient;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.request.CreateStudentRequest;
import com.example.studentservice.response.AddressResponse;
import com.example.studentservice.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class StudentService {
	private  final StudentRepository studentRepository;
	private final WebClient webClient;
	private final AddressFeignClient addressFeignClient;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		student.setAddressId(createStudentRequest.getAddressId());

		student = studentRepository.save(student);

		StudentResponse studentResponse=new StudentResponse(student);
		//Set addres with webclient
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

		//Set address with open feign
		studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));
		return  studentResponse;
	}
	
	public StudentResponse getById (long id) {
		Student student=studentRepository.findById(id).get();
		StudentResponse studentResponse=new StudentResponse(student);
		//Set addres with webclient
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

		//Set address with open feign
		studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));
		return studentResponse;
	}

	//Calling Address microservice
	public AddressResponse getAddressById(long addressId){
		Mono<AddressResponse> addressResponseMono=webClient.get().uri("/getById/"+addressId)
				.retrieve().bodyToMono(AddressResponse.class);
		return addressResponseMono.block();
	}
}
