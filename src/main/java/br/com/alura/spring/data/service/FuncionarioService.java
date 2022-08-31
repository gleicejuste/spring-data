package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class FuncionarioService {

    private Boolean SYSTEM = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CargoRepository cargoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UnidadeRepository unidadeRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeRepository unidadeRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (SYSTEM) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Voltar");
            System.out.println("1 - Inserir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");

            switch (scanner.nextInt()) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    SYSTEM = false;
                    break;
            }

        }
    }

    private void salvar(Scanner scanner){

        Funcionario funcionario = new Funcionario();

        System.out.println("Nome: ");
        funcionario.setNome(scanner.next());

        System.out.println("CPF: ");
        funcionario.setCpf(scanner.next());

        System.out.println("Salário: ");
        funcionario.setSalario(scanner.nextBigDecimal());

        System.out.println("Data Contratação: ");
        String dataContratacao = scanner.next();
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));

        System.out.println("ID do Cargo: ");
        Optional<Cargo> cargo = cargoRepository.findById(scanner.nextInt());
        funcionario.setCargo(cargo.get());

        List<Unidade> unidades = getUnidade(scanner);
        funcionario.setUnidades(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Salvo com sucesso!!");

    }

    private void atualizar(Scanner scanner){
        Funcionario funcionario = new Funcionario();

        System.out.println("Id: ");
        funcionario.setId(scanner.nextInt());

        System.out.println("Nome: ");
        funcionario.setNome(scanner.next());

        funcionarioRepository.save(funcionario);
        System.out.println("Alterado com sucesso!!");

    }

    private void visualizar(Scanner scanner){

        System.out.println("Qual página você deseja visualizar? ");
        Pageable pageable = PageRequest.of(scanner.nextInt(), 10, Sort.by(Sort.Direction.ASC, "nome"));

        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
        System.out.println("Page: " + funcionarios);
        System.out.println("Página atual: " + funcionarios.getNumber());
        System.out.println("Total de Registros: " + funcionarios.getTotalElements());

        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }

    private void deletar(Scanner scanner){
        System.out.println("Id: ");
        funcionarioRepository.deleteById(scanner.nextInt());
        System.out.println("Deletado com sucesso!!");
    }

    private List<Unidade> getUnidade(Scanner scanner) {
        Boolean isTrue = true;
        List<Unidade> unidades = new ArrayList<>();

        while (isTrue) {
            System.out.println("Digite ID Unidade (Para sair digite 0)");
            Integer unidadeId = scanner.nextInt();

            if(unidadeId != 0) {
                Optional<Unidade> unidade = unidadeRepository.findById(unidadeId);
                unidades.add(unidade.get());
            } else {
                isTrue = false;
            }
        }

        return unidades;
    }

}
