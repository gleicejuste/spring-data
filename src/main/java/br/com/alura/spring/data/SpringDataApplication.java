package br.com.alura.spring.data;

import br.com.alura.spring.data.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private Boolean SYSTEM = true;

	private final CargoService cargoService;
	private final FuncionarioService funcionarioService;
	private final UnidadeService unidadeService;
	private final RelatorioService relatorioService;
	private final RelatorioFuncionarioDinamicoService relatorioDinamicoService;

	public SpringDataApplication(CargoService cargoService,
								 FuncionarioService funcionarioService,
								 UnidadeService unidadeService,
								 RelatorioService relatorioService,
								 RelatorioFuncionarioDinamicoService relatorioDinamicoService) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeService = unidadeService;
		this.relatorioService = relatorioService;
		this.relatorioDinamicoService = relatorioDinamicoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		while (SYSTEM){
			System.out.println("Qual estrutura você quer acessar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Unidade");
			System.out.println("3 - Funcionário");
			System.out.println("4 - Relatórios");
			System.out.println("5 - Relatório Dinâmico");

			switch (scanner.nextInt()){
				case 1: cargoService.iniciar(); break;
				case 2: unidadeService.iniciar(); break;
				case 3: funcionarioService.iniciar(); break;
				case 4: relatorioService.iniciar(); break;
				case 5: relatorioDinamicoService.iniciar(scanner); break;
				default: SYSTEM = false; break;
			}

		}

	}
}
