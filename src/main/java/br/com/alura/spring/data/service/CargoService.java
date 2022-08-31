package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CargoService {

    private Boolean SYSTEM = true;
    private final CargoRepository repository;

    public CargoService(CargoRepository repository) {
        this.repository = repository;
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
                    visualizar();
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

        System.out.println("Descrição do cargo: ");
        Cargo cargo = new Cargo();
        cargo.setDescricao(scanner.next());
        repository.save(cargo);
        System.out.println("Salvo com sucesso!!");

    }

    private void atualizar(Scanner scanner){
        Cargo cargo = new Cargo();

        System.out.println("Id: ");
        cargo.setId(scanner.nextInt());

        System.out.println("Descrição: ");
        cargo.setDescricao(scanner.next());

        repository.save(cargo);
        System.out.println("Alterado com sucesso!!");

    }

    private void visualizar(){
        Iterable<Cargo> cargos = repository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo));
    }

    private void deletar(Scanner scanner){
        System.out.println("Id: ");
        repository.deleteById(scanner.nextInt());
        System.out.println("Deletado com sucesso!!");
    }

}
