package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UnidadeService {

    private Boolean SYSTEM = true;
    private final UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository) {
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
        Unidade unidade = new Unidade();

        System.out.println("Descrição: ");
        unidade.setDescricao(scanner.next());

        System.out.println("Endereço: ");
        unidade.setEndereco(scanner.next());

        repository.save(unidade);
        System.out.println("Salvo com sucesso!!");

    }

    private void atualizar(Scanner scanner){
        Unidade unidade = new Unidade();

        System.out.println("Id: ");
        unidade.setId(scanner.nextInt());

        System.out.println("Descrição: ");
        unidade.setDescricao(scanner.next());

        repository.save(unidade);
        System.out.println("Alterado com sucesso!!");

    }

    private void visualizar(){
        Iterable<Unidade> unidades = repository.findAll();
        unidades.forEach(unidade -> System.out.println(unidade));
    }

    private void deletar(Scanner scanner){
        System.out.println("Id: ");
        repository.deleteById(scanner.nextInt());
        System.out.println("Deletado com sucesso!!");
    }


}
