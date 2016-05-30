import corejava.*;
import java.util.*;
import java.sql.*;
import org.postgresql.jdbc4.*;

public class Principal
{	public static void main (String[] args)
	{	Connection conn = null;//crinando uma variavel de conex�o			
				
		try
		{	DriverManager.registerDriver (new org.postgresql.Driver()); 
			conn = DriverManager.getConnection
	    		("jdbc:postgresql://localhost:5432/RepEmpregado","usuario","senha");
			conn.setAutoCommit(false);
		}
		catch (SQLException e)
		{	System.out.println ('\n' + "Erro na conex�o com o banco.");
			e.printStackTrace();
			System.exit(1);
		}
				
		String nome;
		double salario;
		int numDep;
		int numero;
		Empregado umEmpregado;
		Departamento umDepartamento;
		
				
		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um empregado");
			System.out.println('\n' + "2. Alterar um empregado");
			System.out.println('\n' + "3. Remover um empregado");
			System.out.println('\n' + "4. Listar todos os empregados");
			System.out.println('\n' + "5. Exibir departamentos sem empregados");
			System.out.println('\n' + "6. Remover departamento");
			System.out.println('\n' + "7. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 7:");			
				
			switch (opcao)
			{	case 1:
					numero = Console.readInt("Numero: ");
					nome = Console.readLine("Nome: ");
					numDep = Console.readInt("Departamento: ");
					salario = Console.readDouble("Salario: ");
					
					try
					{
						int n = RepositorioEmpregados.inclui(conn, numero, nome, numDep, salario);
						conn.close();
						
						System.out.println("Empregado cadastrado! Numro: "+n);
					}
					catch(SQLException ex)
					{
						ex.printStackTrace();
						System.exit(1);
					}
			
					break;
				case 2:
					
					int resp = Console.readInt('\n' + "Digite o n�mero do empregado que voc� deseja alterar: ");
					
					umEmpregado = RepositorioEmpregados.recuperaUmEmpregado(conn, resp);								
										
					if (umEmpregado != null)
					{	System.out.println('\n' + "N�mero = " + umEmpregado.getNumero() + 
												  "    Nome = " + umEmpregado.getNome() +
												  "    Departamento = " + umEmpregado.getNumDep() +
												  "    Sal�rio = " + umEmpregado.getSalario());
																						
						System.out.println('\n' + "O que voc� deseja alterar?");
						System.out.println('\n' + "1. Nome");
						System.out.println('\n' + "2. Salario");

						int opcaoAlteracao = Console.readInt('\n' + "Digite um n�mero de 1 a 2:");			
					
						switch (opcaoAlteracao)
						{	case 1:
								String novoNome = Console.readLine("Digite o novo nome: ");
								try
								{	if (RepositorioEmpregados.altera(conn, umEmpregado.getNumero(), novoNome,umEmpregado.getNumDep(), umEmpregado.getSalario()))
									{	conn.commit();
										System.out.println('\n' + "Altera��o de nome efetuada com sucesso!");						
									}
									else
										System.out.println('\n' + "Empregado n�o encontrado.");
								}
								catch (SQLException e)
								{	System.out.println ('\n' + "Erro na altera��o de um empregado.");
									e.printStackTrace();
									System.exit(1);
								}
								break;
							case 2:
								double novoSalario = Console.readDouble("Digite o novo sal�rio: ");
								try
								{	if (RepositorioEmpregados.altera(conn, umEmpregado.getNumero(), umEmpregado.getNome(), umEmpregado.getNumDep(), novoSalario))
									{	conn.commit();
										System.out.println('\n' + "Altera��o de sal�rio efetuada com sucesso!");						
									}
									else
										System.out.println('\n' + "Empregado n�o encontrado.");
								}
								catch (SQLException e)
								{	System.out.println ('\n' + "Erro na altera��o de um empregado.");
									e.printStackTrace();
									System.exit(1);
								}
								break;
							default:
								System.out.println('\n' + "Op��o inv�lida!");						
								break;
						}
					}
					else
					{	System.out.println('\n' + "Empregado n�o encontrado!");
					}						
					break;			
				
				case 3:
				{	int resposta = Console.readInt('\n' + "Digite o n�mero do empregado que voc� deseja remover: ");
									
					try
					{	if (RepositorioEmpregados.exclui (conn, resposta))
						{	conn.commit();
							System.out.println('\n' + "Empregado removido com sucesso!");
						}
						else
							System.out.println('\n' + "Empregado n�o encontrado!");
					}		
					catch (SQLException e)
					{	System.out.println ('\n' + "Erro na exclus�o de um empregado.");
						e.printStackTrace();
						System.exit(1);
					}	
						break;
				}
				
				case 4:
				{	ArrayList<Empregado> arrayEmpregados = RepositorioEmpregados.recuperaEmpregados(conn);
									
					if (arrayEmpregados == null)
					{	System.out.println('\n' + "N�o h� empregados cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayEmpregados.size(); i++)
					{	umEmpregado = arrayEmpregados.get(i);
						System.out.println(	"N�mero = " + umEmpregado.getNumero() + 
											"  Nome = " + umEmpregado.getNome() +
											"  Departamento = " + umEmpregado.getNumDep() +
											"  Sal�rio = " + umEmpregado.getSalario());
					}
										
					break;
				}
				
				case 5:
				{	ArrayList<Departamento> arrayDepartamentos = RepositorioDepartamento.recupDptoSemEmp(conn);
									
					if (arrayDepartamentos == null)
					{	System.out.println('\n' + "N�o h� departamentos cadastrados.");
						break;
					}
										
					System.out.println("");
					
					int i;
					int dptovazio = 0;
					for (i = 0; i < arrayDepartamentos.size(); i++)
					{	
						umDepartamento = arrayDepartamentos.get(i);
						int numDpto = umDepartamento.getNumDep();
						if (numDpto == 0)
						{
						dptovazio ++;	
						}					
						System.out.println(	"Departamentos vazios = " + dptovazio);
					}
										
					break;
				}
				
				case 6:
				{	int remove = Console.readInt('\n' + "Digite o n�mero do departamento que voc� deseja remover: ");
									
					try
					{	if (RepositorioDepartamento.exclui (conn, remove))
						{	conn.commit();
							System.out.println('\n' + "Departamento removido com sucesso!");
						}
						else
							System.out.println('\n' + "Departamento n�o encontrado!");
					}		
					catch (SQLException e)
					{	System.out.println ('\n' + "Erro na exclus�o do departamento.");
						e.printStackTrace();
						System.exit(1);
					}	
						break;
				}
				case 7:
					continua = false;
					try
					{	conn.close();
				    }
					catch (SQLException e)
					{	System.out.println ('\n' + "Erro ao fechar a conex�o com o banco de dados.");
						e.printStackTrace();
						System.exit(1);
					}
					break;
				default:
					System.out.println('\n' + "Op��o inv�lida!");						
			}
		}		
	}
}