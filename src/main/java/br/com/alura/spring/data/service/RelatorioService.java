package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {

    private Boolean SYSTEM = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatorioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (SYSTEM) {
            System.out.println("Qual relatório você deseja acessar?");
            System.out.println("0 - Voltar");
            System.out.println("1 - Funcionário por nome");
            System.out.println("2 - Funcionário por nome, data contratacao e salário maior");
            System.out.println("3 - Funcionário por data contratacao maior");
            System.out.println("4 - Pesquisa funcionário salário");

            switch (scanner.nextInt()) {
                case 1: pesqFuncionarioByName(scanner); break;
                case 2: pesqNomeSalarioMaiorContratacao(scanner); break;
                case 3: pesqDataContratacaoMaior(scanner); break;
                case 4: pesqFuncionarioSalario(); break;
                default: SYSTEM = false; break;
            }

        }
    }

    private void pesqFuncionarioByName(Scanner scanner){

        System.out.println("Nome: ");
        List<Funcionario> funcionarios = funcionarioRepository.findByNome(scanner.next());
        funcionarios.forEach(System.out::println);

    }

    private void pesqNomeSalarioMaiorContratacao(Scanner scanner){

        System.out.println("Nome: ");
        String nome = scanner.next();

        System.out.println("Salário: ");
        BigDecimal salario = scanner.nextBigDecimal();

        System.out.println("Data Contratação: ");
        String dataContratacaoString = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(dataContratacaoString, formatter);

        List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorContratacao(nome, salario, dataContratacao);
        funcionarios.forEach(System.out::println);

    }

    private void pesqDataContratacaoMaior(Scanner scanner){

        System.out.println("Data Contratação: ");

        List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(LocalDate.parse(scanner.next(), formatter));
        funcionarios.forEach(System.out::println);

    }

    private void pesqFuncionarioSalario(){

        List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();
        funcionarios.forEach(funcionario -> System.out.println("Funcionário [id: " + funcionario.getId()
        + " | nome: " + funcionario.getNome() + " | salário: " + funcionario.getSalario()));

    }


}
