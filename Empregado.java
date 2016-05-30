public class Empregado
{	
	
	private int numero;
	private String nome;
	private int numDep;
	private double salario;
	
	
	public Empregado (int numero, String nome, int numDep, double salario)
	{	this.numero = numero;
		this.nome = nome;
		this.numDep = numDep;
		this.salario = salario;
	}

	public int getNumero()
	{	return numero;
	}

	public String getNome()
	{	return nome;
	}
	
	public int getNumDep()
	{
		return numDep;
	}
	
	public double getSalario()
	{	return salario;
	}
	
}