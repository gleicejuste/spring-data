package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioFuncionarioDinamicoService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatorioFuncionarioDinamicoService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    public void iniciar(Scanner scanner){

        System.out.println("Nome: ");
        String nome = scanner.next();
        if(nome.equalsIgnoreCase("NULL")){
            nome = null;
        }

        System.out.println("CPF: ");
        String cpf = scanner.next();
        if(cpf.equalsIgnoreCase("NULL")){
            cpf = null;
        }

        System.out.println("Salário: ");
        BigDecimal salario = scanner.nextBigDecimal();
        if(salario.equals(BigDecimal.ZERO)){
            salario = null;
        }

        System.out.println("Data Contratação: ");
        String dataString = scanner.next();
        LocalDate data;
        if(dataString.equals("NULL")){
            data = null;
        }else{
            data = LocalDate.parse(dataString, formatter);
        }

        List<Funcionario> funcionarios = funcionarioRepository.findAll(
                Specification.where(
                        SpecificationFuncionario.nome(nome))
                        .or(SpecificationFuncionario.cpf(cpf))
                        .or(SpecificationFuncionario.dataContratacao(data))
                        .or(SpecificationFuncionario.salario(salario))
        );

        funcionarios.forEach(System.out::println);
    }

}
