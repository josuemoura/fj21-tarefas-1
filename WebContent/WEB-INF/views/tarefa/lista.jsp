<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="resources/js/jquery-1.9.1.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link type="text/css" href="resources/css/tarefas.css" rel="stylesheet" />
	<title>Black List</title>
</head>
<body>
	<script type="text/javascript">
		function finalizaAgora(id) {
			$.post("finalizaTarefa", {'id' : id}, function(resposta) {
			$("#tarefa_"+id).html(resposta);
			});
		}
	</script>
	
	<script type="text/javascript">
		function excluir(id) {
			$.post("removeTarefa2", {'id' : id}, function() {
			$("#tabelaTarefas_"+id).closest("tr").hide();
			});
		}
	</script>
	
	<a href="novaTarefa">Criar nova tarefa</a>
	<br /><br />
	
	<table>
		<tr>
			<th>Id</th>
			<th>Descrição</th>
			<th>Finalizado</th>
			<th>Data de Finalização</th>
		</tr>
		
			<c:forEach items="${tarefas}" var="tarefa">
				<tr id="tarefa_${tarefa.id}">
					<td>${tarefa.id}</td>
					<td>${tarefa.descricao}</td>
					<c:if test="${tarefa.finalizado eq true}">
						<td>Finalizado</td>
					</c:if>
					<c:if test="${tarefa.finalizado eq false}">
						<td >
							<a href="#" onClick="finalizaAgora(${tarefa.id})">
								Finalizar
							</a>
						</td>
					</c:if>
					
					
					<td>
						<fmt:formatDate
							value="${tarefa.dataFinalizacao.time}"
							pattern="dd/MM/yyyy"/>
					</td>
					<td><a href="removeTarefa?id=${tarefa.id}">Remover</a></td>
					<td><a href="mostraTarefa?id=${tarefa.id}">Alterar</a></td>
					
<%-- 					<td><a href="#" onClick="excluir(${tarefa.id})">Excluir</a></td> --%>
				</tr>
			</c:forEach>
	</table>
</body>
</html>