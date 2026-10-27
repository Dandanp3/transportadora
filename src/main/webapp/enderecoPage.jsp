<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Endereços | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">

            <header class="topo">
                <div class="topo-titulo">
                    <h1>Endereços</h1>
                    <p>Gerencie os locais de coleta e entrega vinculados aos clientes.</p>
                </div>

                <a href="${pageContext.request.contextPath}/enderecos?acao=novo" class="btn-principal">
                    <i class="ph ph-plus" aria-hidden="true"></i>
                    Novo endereço
                </a>
            </header>

            <div class="conteudo-principal">

                <!-- status -->
                <c:choose>
                    <c:when test="${param.status == 'sucesso'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Endereço cadastrado com sucesso!</div>
                    </c:when>
                    <c:when test="${param.status == 'atualizado'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Endereço atualizado!</div>
                    </c:when>
                    <c:when test="${param.status == 'excluido'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-trash" aria-hidden="true"></i> Endereço excluído do sistema.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_unico_endereco'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i> Não é possível excluir. Todo cliente precisa de pelo menos 1 endereço cadastrado.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_exclusao'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i> Não foi possível excluir o endereço. Ele pode estar vinculado a uma entrega existente.</div>
                    </c:when>
                </c:choose>

                

                <!-- cards -->
                <section class="resumo" aria-label="Resumo de endereços">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Total de endereços</span>
                        <strong class="resumo-valor">${totalEnderecos != null ? totalEnderecos : 0}</strong>
                    </div>
                </section>

                <section class="painel" aria-labelledby="titulo-lista-enderecos">
                    <h2 id="titulo-lista-enderecos" class="sr-only">Lista de endereços</h2>

                    <div class="lista">
                        <div class="tabela-wrapper" role="region" aria-label="Tabela de endereços (role a tabela horizontalmente se necessário)" tabindex="0">
                            
                            <!-- busca -->
                        <form class="busca" action="${pageContext.request.contextPath}/produtos" method="get" role="search">
                            <div class="busca-campo">
                                <i class="ph ph-magnifying-glass"></i>
                                <input type="search"
                                    name="q"
                                    placeholder="Buscar pela NF-e"
                                    aria-label="Buscar enderecos"
                                    data-busca-tabela="tabela-enderecos"
                                    data-busca-colunas="0, 2"
                                    data-busca-modo="texto"
                                    data-busca-vazio="Nenhum endereço encontrado para essa busca.">
                            </div>
                        </form>


                            <table class="tabela-dados" id="tabela-enderecos">
                                <caption class="sr-only">Endereços cadastrados</caption>
                                <thead>
                                    <tr>
                                        <th scope="col">Endereço</th>
                                        <th scope="col">Localidade</th>
                                        <th scope="col">Pertence a (Cliente)</th>
                                        <th scope="col" class="col-acoes"><span class="sr-only">Ações</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty enderecos}">
                                            <tr>
                                                <td colspan="4" style="text-align: center; padding: 32px; color: #475569;">
                                                    Nenhum endereço cadastrado ainda. Clique em "Novo endereço" para começar.
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="endereco" items="${enderecos}">
                                                <tr>
                                                    <td>
                                                        <span class="dado-principal">
                                                            <c:out value="${endereco.logradouro}"/>, <c:out value="${endereco.numero == 0 ? 'S/N' : endereco.numero}"/>
                                                            <c:if test="${not empty endereco.complemento}">
                                                                - <c:out value="${endereco.complemento}"/>
                                                            </c:if>
                                                        </span>
                                                        <span class="dado-secundario">
                                                            Bairro: <c:out value="${endereco.bairro}"/> | CEP: <c:out value="${endereco.cep}"/>
                                                        </span>
                                                    </td>

                                                    <td>
                                                        <span class="dado-principal">
                                                            <c:out value="${endereco.cidade}"/> - <c:out value="${endereco.uf}"/>
                                                        </span>
                                                    </td>

                                                    <td>
                                                        <span class="dado-principal">
                                                            <c:out value="${endereco.cliente.nome != null ? endereco.cliente.nome : 'Cliente ID: ' += endereco.clienteId}"/>
                                                        </span>
                                                    </td>

                                                    <td class="col-acoes">
                                                        <a href="${pageContext.request.contextPath}/enderecos?acao=editar&id=${endereco.id}"
                                                           class="btn-icone" aria-label="Editar endereço <c:out value='${endereco.logradouro}'/>, <c:out value='${endereco.numero == 0 ? "S/N" : endereco.numero}'/>">
                                                            <i class="ph ph-pencil-simple" aria-hidden="true"></i>
                                                        </a>

                                                        <form action="${pageContext.request.contextPath}/enderecos" method="post" style="display:inline; margin:0;">
                                                            <input type="hidden" name="acaoForm" value="excluirEndereco">
                                                            <input type="hidden" name="id" value="${endereco.id}">
                                                            <button type="submit" class="btn-icone btn-perigo" style="border: 0; background: none;"
                                                                    aria-label="Excluir endereço <c:out value='${endereco.logradouro}'/>, <c:out value='${endereco.numero == 0 ? "S/N" : endereco.numero}'/>"
                                                                    onclick="return confirm('Apagar este endereço? Esta ação não pode ser desfeita.')">
                                                                <i class="ph ph-trash" aria-hidden="true"></i>
                                                            </button>
                                                        </form>
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
<script src="./assets/js/busca.js"></script>
</html>