import corejava.*;
import java.util.*;
import java.sql.*;
import org.postgresql.jdbc4.*;

public class RepositorioEmpregados
{	

	public static int inclui(Connection conn, int numero, String nome, int numDep, double salario) throws SQLException
	{		
		//pesquisando o ultimo indice da tabela
		PreparedStatement pstmt = conn.prepareStatement("SELECT Empregados_seq.nextval AS contador FROM SYS.DUAL");
		
		ResultSet rs = pstmt.executeQuery();//rs cursor para o ultimo indice da tabela
		
		rs.next();
		
		int pk = rs.getInt("contador");//pegando o próximo indice da chave primária
		
		rs.close();
		
		pstmt.close();
		
		pstmt = conn.prepareStatement("INSERT INTO Empregados (numero, nome, numDep, salario)" + "VALUES (?,?,?,?)");
		
		pstmt.setInt(1, pk);
		pstmt.setString(2, nome);
		pstmt.setInt(3, numDep);
		pstmt.setDouble(4, salario);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		
		return pk;
	}
	public static boolean altera (Connection conn, int numero, String nome, int numDep, double salario) throws SQLException
	{		
		PreparedStatement pstmt = conn.prepareStatement 
			("UPDATE EMPREGADOS SET nome = ?, numDep = ?, salario = ? WHERE numero = ?");
				
		pstmt.setString(1, nome);
		pstmt.setInt(2, numDep);
		pstmt.setDouble(3, salario);	
		pstmt.setInt(4, numero);	
				
		int n = pstmt.executeUpdate();//conferindo quantas linhas foram afetadas
		
		pstmt.close();
		
		if (n == 1)
		{	return true;
		}
		else
			return false;
	}


	public static boolean exclui (Connection conn, int numDep)throws SQLException
	{	
		//preparando a query
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM empregado WHERE numero = ?");
		
		//configurando bindvalue
		pstmt.setInt(1, numDep);
		
		//executando a query
		int n = pstmt.executeUpdate();
		
		//fechando a instancia da conexão
		pstmt.close();
		
		//verificando se a alteração obteve sucesso
		if(n==1)
			return true;
		else
			return false;
	}
	
	public static ArrayList<Empregado> recuperaEmpregados(Connection conn)
	{	
		ArrayList <Empregado> listaDeEmpregados =null;
		
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM empregados");
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				listaDeEmpregados = new ArrayList<Empregado>(20);
				
				do
				{
					listaDeEmpregados.add(new Empregado(rs.getInt("numero"), rs.getString("nome"),rs.getInt("departamento"), rs.getDouble("salario")));
				}
				while(rs.next());
			}
			
			rs.close();
			pstmt.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		
		return listaDeEmpregados;
	}
	
	public static Empregado recuperaUmEmpregado(Connection conn, int numero)
	{	
		Empregado e = null;
		
		try
		{
			PreparedStatement ptsmt = conn.prepareStatement("SELECT numero, nome, numDep, salario FROM empregado WHERE numero = ?");
			
			ptsmt.setInt(1, numero);

			ResultSet rs = ptsmt.executeQuery();//Resultset recebe a busca
			
			if(rs.next())//verificando se há outro registro após o buscado
				e = new Empregado (rs.getInt("numero"), rs.getString("nome"),rs.getInt("numDep"), rs.getDouble("salario"));
			
			rs.close();
			ptsmt.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		
		return e;
	}
}