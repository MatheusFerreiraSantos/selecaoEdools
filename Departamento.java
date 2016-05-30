public class Departamento
{
  private String nome;
  private int numDep;

  public Departamento (String nome, int numDep)
  {
    this.nome = nome;
    this.numDep = numDep;
  }

  public int getNumDep()
  { return numDep;
  }

  public String getNome()
  {return nome;
  }
}
