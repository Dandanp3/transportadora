<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Entrega | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/entregas" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Nova Entrega</h1>
                        <p>Defina a origem, destino, produto e o tamanho da caixa.</p>
                    </div>
                </header>

                <form action="${pageContext.request.contextPath}/entregas" method="post">
                    
                    <!-- BLOCO 1: ENVOLVIDOS -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-users"></i> Envolvidos no Transporte</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="remetenteId">Remetente (Quem envia)</label>
                                <select id="remetenteId" name="remetenteId" required>
                                    <option value="" disabled selected>Escolha um cliente...</option>
                                    <c:forEach var="cliente" items="${clientes}">
                                        <option value="${cliente.id}">${cliente.nome} (${cliente.documentoFormatado})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="destinatarioId">Destinatário (Quem recebe)</label>
                                <select id="destinatarioId" name="destinatarioId" required>
                                    <option value="" disabled selected>Escolha um cliente...</option>
                                    <c:forEach var="cliente" items="${clientes}">
                                        <option value="${cliente.id}">${cliente.nome} (${cliente.documentoFormatado})</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </section>

                    <!-- BLOCO 2: MUNDO FISCAL (Conteúdo) -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-receipt"></i> Conteúdo (Nota Fiscal)</h2>
                        <div class="form-grid">
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="produtoId">Produto da Tabela</label>
                                <select id="produtoId" name="produtoId" required>
                                    <option value="" disabled selected>Escolha um produto cadastrado...</option>
                                    <!-- Temporariamente hardcoded, ideal buscar do backend -->
                                    <option value="1">Produto Genérico para Teste</option> 
                                </select>
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="quantidade">Quantidade</label>
                                <!-- Bloqueia valores menores que 1 e maiores que 9.999 -->
                                <input type="number" id="quantidade" name="quantidade" value="1" min="1" max="9999" required>
                            </div>
                        </div>
                    </section>

                    <!-- BLOCO 3: MUNDO FÍSICO (A Caixa) -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-package"></i> Dimensões do Pacote</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="peso">Peso (Kg)</label>
                                <!-- Aceita decimais (step=0.01), não pode ser 0 ou negativo, máximo 5000kg -->
                                <input type="number" id="peso" name="peso" step="0.01" min="0.01" max="5000" placeholder="Ex: 1.5" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="altura">Alt. (cm)</label>
                                <!-- Min 1cm, max 1000cm (10 metros) -->
                                <input type="number" id="altura" name="altura" min="1" max="1000" placeholder="20" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="largura">Larg. (cm)</label>
                                <input type="number" id="largura" name="largura" min="1" max="1000" placeholder="20" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="comprimento">Comp. (cm)</label>
                                <input type="number" id="comprimento" name="comprimento" min="1" max="1000" placeholder="20" required>
                            </div>
                        </div>
                    </section>

                    <div class="cadastro-rodape">
                        <a href="${pageContext.request.contextPath}/entregas" class="btn-secundario">Cancelar</a>
                        <button type="submit" class="btn-principal">
                            <i class="ph ph-truck"></i> Gerar Entrega e Frete
                        </button>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>