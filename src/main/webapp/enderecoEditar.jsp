<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Endereço | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <style>
        .alerta-erro { display: flex; gap: 12px; background-color: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 12px; margin-bottom: 24px; }
        .alerta-erro i { font-size: 24px; color: #ef4444; }
    </style>
</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <!-- Botao de voltar joga de volta pra tela de edicao daquele cliente -->
                    <a href="${pageContext.request.contextPath}/clientes?acao=editar&id=${clienteId}" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Editar Endereço</h1>
                        <p>Atualize os dados de localidade.</p>
                    </div>
                </header>

                <c:if test="${not empty erros}">
                    <div class="alerta-erro">
                        <i class="ph ph-warning-circle"></i>
                        <div>
                            <strong>Verifica esses campos ai:</strong>
                            <ul><c:forEach var="erro" items="${erros}"><li>${erro}</li></c:forEach></ul>
                        </div>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/clientes" method="post">
                    <input type="hidden" name="acaoForm" value="atualizarEndereco">
                    <input type="hidden" name="enderecoId" value="${endereco.id}">
                    <input type="hidden" name="clienteId" value="${clienteId}">

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-map-pin"></i> Dados do Endereço</h2>
                        <div class="form-grid">
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="cep">CEP</label>
                                <input type="text" id="cep" name="cep" value="${endereco.cep}" minlength="9" maxlength="9" pattern="\d{5}-\d{3}" required>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="logradouro">Logradouro (Rua, Av)</label>
                                <input type="text" id="logradouro" name="logradouro" value="${endereco.logradouro}" minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="numero">Número</label>
                                <input type="text" id="numero" name="numero" value="${endereco.numero == 0 ? 'S/N' : endereco.numero}" maxlength="10" required>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="complemento">Complemento <span class="opcional">(opcional)</span></label>
                                <input type="text" id="complemento" name="complemento" value="${endereco.complemento}" maxlength="50">
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="bairro">Bairro</label>
                                <input type="text" id="bairro" name="bairro" value="${endereco.bairro}" minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="cidade">Cidade</label>
                                <input type="text" id="cidade" name="cidade" value="${endereco.cidade}" minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="uf">UF</label>
                                <select id="uf" name="uf" required>
                                    <option value="" disabled>--</option>
                                    <option value="PE" ${endereco.uf == 'PE' ? 'selected' : ''}>PE</option>
                                    <option value="PB" ${endereco.uf == 'PB' ? 'selected' : ''}>PB</option>
                                    <option value="SP" ${endereco.uf == 'SP' ? 'selected' : ''}>SP</option>
                                    <!-- Cola o resto dos estados aqui dps pra n ficar gigante o codigo -->
                                </select>
                            </div>
                        </div>
                        
                        <div style="margin-top: 16px; text-align: right;">
                            <button type="submit" class="btn-principal"><i class="ph ph-floppy-disk"></i> Salvar Endereço</button>
                        </div>
                    </section>
                </form>
            </div>
        </main>
    </div>

    <!-- Mascaras -->
    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>