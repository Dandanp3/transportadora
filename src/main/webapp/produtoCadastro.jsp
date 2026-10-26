<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>

<%-- editando = veio da tela de edição (produto carregado) OU voltou de um erro de validação em modo edição --%>
<c:set var="editando" value="${not empty produto or param.acaoForm == 'atualizarProduto'}" />
<c:set var="idAtual" value="${not empty produto ? produto.id : param.id}" />
<%-- com erro (ou cadastro novo) os campos vêm do que a pessoa digitou; senão, do banco já formatado em pt-BR --%>
<c:set var="usarParam" value="${not empty erros or empty produto}" />
<c:choose>
    <c:when test="${usarParam}">
        <c:set var="descCampo" value="${param.descricao}" />
        <c:set var="valorCampo" value="${param.valorPadrao}" />
        <c:set var="pesoCampo" value="${param.pesoPadrao}" />
    </c:when>
    <c:otherwise>
        <c:set var="descCampo" value="${produto.descricao}" />
        <fmt:formatNumber var="valorCampo" value="${produto.valorPadrao}" minFractionDigits="2" maxFractionDigits="2" groupingUsed="false" />
        <fmt:formatNumber var="pesoCampo" value="${produto.pesoPadrao}" minFractionDigits="3" maxFractionDigits="3" groupingUsed="false" />
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty erros ? 'Erro: ' : ''}${editando ? 'Editar' : 'Novo'} Produto | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
    <style>
        .alerta-erro { background: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 8px; margin-bottom: 20px; }
        .alerta-erro strong { display: flex; align-items: center; gap: 8px; }
        .alerta-erro ul { margin: 8px 0 0 20px; }
    </style>
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/produtos" class="btn-voltar" aria-label="Voltar para a lista de produtos">
                        <i class="ph ph-arrow-left" aria-hidden="true"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>${editando ? 'Editar' : 'Novo'} Produto</h1>
                        <p>Preencha os dados do catálogo de carga. Todos os campos são obrigatórios.</p>
                    </div>
                </header>

                <c:if test="${not empty erros}">
                    <div class="alerta-erro" role="alert" tabindex="-1" autofocus>
                        <strong><i class="ph ph-warning-circle" aria-hidden="true"></i> Corrija os itens abaixo para continuar:</strong>
                        <ul>
                            <c:forEach var="erro" items="${erros}">
                                <li><c:out value="${erro}"/></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/produtos" method="post">
                    <input type="hidden" name="id" value="<c:out value='${idAtual}'/>">
                    <c:if test="${editando}">
                        <input type="hidden" name="acaoForm" value="atualizarProduto">
                    </c:if>

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-package" aria-hidden="true"></i> Dados do Produto</h2>
                        <div class="form-grid">
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="descricao">Descrição do produto (nome ou categoria)</label>
                                <input type="text" id="descricao" name="descricao" placeholder="Ex: Caixa genérica até 5kg"
                                       value="<c:out value='${descCampo}'/>"
                                       minlength="3" maxlength="150" required>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="valorPadrao">Valor padrão (R$)</label>
                                <input type="text" id="valorPadrao" name="valorPadrao" placeholder="0,00"
                                       value="<c:out value='${valorCampo}'/>"
                                       inputmode="decimal" autocomplete="off"
                                       oninput="mascaraMoeda(this)" required>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="pesoPadrao">Peso padrão (kg)</label>
                                <input type="text" id="pesoPadrao" name="pesoPadrao" placeholder="0,000"
                                       value="<c:out value='${pesoCampo}'/>"
                                       inputmode="decimal" autocomplete="off"
                                       oninput="mascaraPeso(this)" required>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="unidade">Unidade de Medida</label>
                                <select name="unidadeMedida" id="unidadeMedida-${produto.id}" style="cursor: pointer;">
                                    <option value="KG" ${produto.unidadeMedida == 'KG' || empty produto ? 'selectec' : ''}>Kg</option>
                                    <option value="L" ${produto.unidadeMedida == 'L' ? 'selected' : ''}>Litros</option>
                                    <option value="UN" ${produto.unidadeMedida == 'UN' ? 'selected' : ''}>Unidade</option>
                                </select>
                            </div>
                        </div>
                    </section>

                    <div class="cadastro-rodape">
                        <c:if test="${editando}">
                            <button type="submit" form="form-excluir-produto" formnovalidate
                                    class="btn-secundario btn-perigo" style="margin-right: auto;"
                                    onclick="return confirm('Excluir este produto do catálogo? Esta ação não pode ser desfeita.')">
                                <i class="ph ph-trash" aria-hidden="true"></i> Excluir produto
                            </button>
                        </c:if>

                        <a href="${pageContext.request.contextPath}/produtos" class="btn-secundario">Cancelar</a>

                        <button type="submit" class="btn-principal">
                            <i class="${editando ? 'ph ph-floppy-disk' : 'ph ph-check'}" aria-hidden="true"></i>
                            ${editando ? 'Atualizar produto' : 'Salvar produto'}
                        </button>
                    </div>
                </form>

                <c:if test="${editando}">
                    <form id="form-excluir-produto" action="${pageContext.request.contextPath}/produtos" method="post">
                        <input type="hidden" name="acaoForm" value="excluirProduto">
                        <input type="hidden" name="id" value="<c:out value='${idAtual}'/>">
                    </form>
                </c:if>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>
