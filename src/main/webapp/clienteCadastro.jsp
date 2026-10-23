<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty erros ? 'Erro: ' : ''}Novo Cliente | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
    <style>
        .alerta-erro { background:#fef2f2; border:1px solid #fca5a5; color:#991b1b; border-radius:8px; padding:14px 18px; margin-bottom:20px; }
        .alerta-erro strong { display:flex; align-items:center; gap:8px; margin-bottom:6px; }
        .alerta-erro ul { margin:0; padding-left:22px; }
    </style>
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/clientes" class="btn-voltar" aria-label="Voltar para a lista de clientes">
                        <i class="ph ph-arrow-left" aria-hidden="true"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Novo Cliente</h1>
                        <p>Preencha os dados pessoais e o endereço de coleta/faturamento. Campos sem "(opcional)" são obrigatórios.</p>
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

                <form action="${pageContext.request.contextPath}/clientes" method="post">

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-user" aria-hidden="true"></i> Dados do Cliente</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="documento">CPF / CNPJ</label>
                                <input type="text" id="documento" name="documento" placeholder="Somente números"
                                       value="<c:out value='${form.documento}'/>"
                                       inputmode="numeric" autocomplete="off" aria-describedby="ajuda-documento"
                                       minlength="11" maxlength="18" required>
                                <p id="ajuda-documento" class="campo-ajuda">Digite só os números; a formatação é automática.</p>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="telefone">Telefone</label>
                                <input type="tel" id="telefone" name="telefone" placeholder="(00) 00000-0000"
                                       value="<c:out value='${form.telefone}'/>"
                                       inputmode="tel" autocomplete="tel"
                                       minlength="10" maxlength="15" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nome">Nome completo ou Razão Social</label>
                                <input type="text" id="nome" name="nome" placeholder="Digite o nome completo"
                                       value="<c:out value='${form.nome}'/>"
                                       autocomplete="name" minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nomeFantasia">Nome Fantasia <span class="opcional">(opcional)</span></label>
                                <input type="text" id="nomeFantasia" name="nomeFantasia"
                                       value="<c:out value='${form.nomeFantasia}'/>"
                                       autocomplete="organization" maxlength="100">
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="email">E-mail</label>
                                <input type="email" id="email" name="email" placeholder="contato@empresa.com"
                                       value="<c:out value='${form.email}'/>"
                                       autocomplete="email" maxlength="100" required>
                            </div>
                        </div>
                    </section>

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-map-pin" aria-hidden="true"></i> Endereço</h2>
                        <div class="form-grid">
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="cep">CEP</label>
                                <input type="text" id="cep" name="cep" placeholder="00000-000"
                                       value="<c:out value='${form.cep}'/>"
                                       inputmode="numeric" autocomplete="postal-code"
                                       minlength="9" maxlength="9" pattern="\d{5}-\d{3}" title="Digite um CEP válido no formato 00000-000" required>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="logradouro">Logradouro (rua, avenida)</label>
                                <input type="text" id="logradouro" name="logradouro"
                                       value="<c:out value='${form.logradouro}'/>"
                                       autocomplete="address-line1" minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="numero">Número</label>
                                <input type="text" id="numero" name="numero" placeholder="Ex: 123"
                                       value="<c:out value='${form.numero}'/>"
                                       inputmode="numeric" pattern="\d{1,9}|[Ss]/[Nn]" maxlength="9"
                                       title="Digite apenas números ou S/N" aria-describedby="ajuda-numero" required>
                                <p id="ajuda-numero" class="campo-ajuda">Sem número? Digite S/N.</p>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="complemento">Complemento <span class="opcional">(opcional)</span></label>
                                <input type="text" id="complemento" name="complemento" placeholder="Apto, Sala, Galpão"
                                       value="<c:out value='${form.complemento}'/>"
                                       autocomplete="address-line2" maxlength="50">
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="bairro">Bairro</label>
                                <input type="text" id="bairro" name="bairro"
                                       value="<c:out value='${form.bairro}'/>"
                                       minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="cidade">Cidade</label>
                                <input type="text" id="cidade" name="cidade"
                                       value="<c:out value='${form.cidade}'/>"
                                       autocomplete="address-level2" minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="uf">UF</label>
                                <c:set var="ufSelecionada" value="${form.uf}" />
                                <select id="uf" name="uf" autocomplete="address-level1" required>
                                    <option value="" disabled ${empty ufSelecionada ? 'selected' : ''}>--</option>
                                    <option value="AC" ${ufSelecionada == 'AC' ? 'selected' : ''}>AC</option>
                                    <option value="AL" ${ufSelecionada == 'AL' ? 'selected' : ''}>AL</option>
                                    <option value="AP" ${ufSelecionada == 'AP' ? 'selected' : ''}>AP</option>
                                    <option value="AM" ${ufSelecionada == 'AM' ? 'selected' : ''}>AM</option>
                                    <option value="BA" ${ufSelecionada == 'BA' ? 'selected' : ''}>BA</option>
                                    <option value="CE" ${ufSelecionada == 'CE' ? 'selected' : ''}>CE</option>
                                    <option value="DF" ${ufSelecionada == 'DF' ? 'selected' : ''}>DF</option>
                                    <option value="ES" ${ufSelecionada == 'ES' ? 'selected' : ''}>ES</option>
                                    <option value="GO" ${ufSelecionada == 'GO' ? 'selected' : ''}>GO</option>
                                    <option value="MA" ${ufSelecionada == 'MA' ? 'selected' : ''}>MA</option>
                                    <option value="MT" ${ufSelecionada == 'MT' ? 'selected' : ''}>MT</option>
                                    <option value="MS" ${ufSelecionada == 'MS' ? 'selected' : ''}>MS</option>
                                    <option value="MG" ${ufSelecionada == 'MG' ? 'selected' : ''}>MG</option>
                                    <option value="PA" ${ufSelecionada == 'PA' ? 'selected' : ''}>PA</option>
                                    <option value="PB" ${ufSelecionada == 'PB' ? 'selected' : ''}>PB</option>
                                    <option value="PR" ${ufSelecionada == 'PR' ? 'selected' : ''}>PR</option>
                                    <option value="PE" ${ufSelecionada == 'PE' ? 'selected' : ''}>PE</option>
                                    <option value="PI" ${ufSelecionada == 'PI' ? 'selected' : ''}>PI</option>
                                    <option value="RJ" ${ufSelecionada == 'RJ' ? 'selected' : ''}>RJ</option>
                                    <option value="RN" ${ufSelecionada == 'RN' ? 'selected' : ''}>RN</option>
                                    <option value="RS" ${ufSelecionada == 'RS' ? 'selected' : ''}>RS</option>
                                    <option value="RO" ${ufSelecionada == 'RO' ? 'selected' : ''}>RO</option>
                                    <option value="RR" ${ufSelecionada == 'RR' ? 'selected' : ''}>RR</option>
                                    <option value="SC" ${ufSelecionada == 'SC' ? 'selected' : ''}>SC</option>
                                    <option value="SP" ${ufSelecionada == 'SP' ? 'selected' : ''}>SP</option>
                                    <option value="SE" ${ufSelecionada == 'SE' ? 'selected' : ''}>SE</option>
                                    <option value="TO" ${ufSelecionada == 'TO' ? 'selected' : ''}>TO</option>
                                </select>
                            </div>
                        </div>
                    </section>

                    <div class="cadastro-rodape">
                        <a href="${pageContext.request.contextPath}/clientes" class="btn-secundario">Cancelar</a>
                        <button type="submit" class="btn-principal">
                            <i class="ph ph-check" aria-hidden="true"></i> Salvar Cliente
                        </button>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>
