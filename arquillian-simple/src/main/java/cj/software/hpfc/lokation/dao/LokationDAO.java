package cj.software.hpfc.lokation.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@Dependent
public class LokationDAO
{
	@Inject
	private Session session;

	public List<String> readLokationen()
	{
		String lQL = "SELECT bezeichnung FROM lokation WHERE bucket = ?";
		PreparedStatement lStmt = this.session.prepare(lQL);
		BoundStatement lBoundStmt = lStmt.bind(1);
		ResultSet lRS = this.session.execute(lBoundStmt);
		List<String> lResult = new ArrayList<>();
		for (Row bRow : lRS.all())
		{
			String lBezeichnung = bRow.getString("bezeichnung");
			lResult.add(lBezeichnung);
		}
		return lResult;
	}
}
