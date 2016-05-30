
import corejava.*;
import java.util.*;
import java.sql.*;
import org.postgresql.jdbc4.*;

public class RepositorioDepartamento
{	


	public static boolean exclui (Connection conn, int numero)throws SQLException
	{	
		//preparando a query
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Departamento WHERE numero = ?");
		
		//configurando bindvalue
		pstmt.setInt(1, numero);
		
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
	public static ArrayList<Departamento> recupDptoSemEmp(Connection conn)
	{	
		ArrayList <Departamento> listaDeDepartamento =null;
		
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM departamentos");
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				listaDeDepartamento = new ArrayList<Departamento>(20);
				
				do
				{
					listaDeDepartamento.add(new Departamento(rs.getString("nome"), rs.getInt("numero")));
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
		
		return listaDeDepartamento;
	}
}
