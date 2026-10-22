<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produtos | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <!-- reaproveitando o css da lista que agr é generico -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listas.css">
</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <header class="topo">
                <div class="topo-titulo">
                    <h1>Catálogo de Produtos</h1>
                    <p>Gerencie a tabela padrão de fretes e cargas.</p>
                </div>

                <button type="button" class="btn-principal" onclick="window.location.href='${pageContext.request.contextPath}/produtos?acao=novo'">
                    <i class="ph ph-plus"></i>
                    Novo Produto
                </button>
            </header>

            <div class="conteudo-principal">
                <section class="painel">
                    <!-- busca -->
                    <form class="busca" action="${pageContext.request.contextPath}/produtos" method="get" role="search">
                        <div class="busca-campo">
                            <i class="ph ph-magnifying-glass"></i>
                            <input type="search"
                                   name="q"
                                   placeholder="Buscar por descrição ou ID..."
                                   aria-label="Buscar produtos">
                        </div>
                    </form>

                    <!-- lista -->
                    <div class="lista">
                        <div class="tabela-wrapper">
                            <table class="tabela-dados">
                                <thead>
                                    <tr>
                                        <th>Descrição do Produto</th>
                                        <th>Valor Base (R$)</th>
                                        <th>Peso Base (KG)</th>
                                        <th class="col-acoes"><span class="sr-only">Ações</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty produtos}">
                                            <tr>
                                                <td colspan="4" style="text-align: center; color: #64748b;">Nenhum produto cadastrado no catálogo.</td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="produto" items="${produtos}">
                                                <tr>
                                                    <td>
                                                        <span class="dado-principal">
                                                            <c:out value="${produto.descricao}"/>
                                                        </span>
                                                        <span class="dado-secundario">
                                                            ID: <c:out value="${produto.id}"/>
                                                        </span>
                                                    </td>

                                                    <td>
                                                        <span class="valor-destaque">
                                                            <fmt:formatNumber value="${produto.valorPadrao}" type="currency" currencySymbol="R$"/>
                                                        </span>
                                                    </td>
                                                    
                                                    <td>
                                                        <c:out value="${produto.pesoPadrao}"/> kg
                                                    </td>

                                                    <td class="col-acoes">
                                                        <a href="${pageContext.request.contextPath}/produtos?acao=editar&id=${produto.id}" 
                                                           class="btn-icone" aria-label="Editar produto" title="Editar">
                                                            <i class="ph ph-pencil-simple"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>
</body>
</html>