<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Cliente | Dandan Transportes</title>
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
                    <a href="${pageContext.request.contextPath}/clientes" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Novo Cliente</h1>
                        <p>Preencha os dados pessoais e o endereço de coleta/faturamento.</p>
                    </div>
                </header>

                <form action="${pageContext.request.contextPath}/clientes" method="post">
                    <!-- guarda o id do cliente se for um update -->
                    <input type="hidden" name="id" value="${cliente.id}">

                    
                    <!-- dados 1 -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-user"></i> Dados do Cliente</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="documento">CPF / CNPJ</label>
                                <input type="text" id="documento" name="documento" placeholder="Apenas números" 
                                       value="${cliente != null ? cliente.documento : param.documento}"
                                       minlength="14" maxlength="18" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="telefone">Telefone</label>
                                <input type="tel" id="telefone" name="telefone" placeholder="(00) 00000-0000" 
                                       value="${cliente != null ? cliente.telefone : param.telefone}"
                                       minlength="14" maxlength="15" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nome">Nome completo ou Razão Social</label>
                                <input type="text" id="nome" name="nome" placeholder="Digite o nome completo"
                                       value="${cliente != null ? cliente.nome : param.nome}"
                                       minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nomeFantasia">Nome Fantasia <span class="opcional">(Opcional para PJ)</span></label>
                                <input type="text" id="nomeFantasia" name="nomeFantasia" 
                                       value="${cliente != null ? cliente.nomeFantasia : param.nomeFantasia}"
                                       maxlength="100">
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="email">E-mail</label>
                                <input type="email" id="email" name="email" placeholder="contato@empresa.com" 
                                       value="${cliente != null ? cliente.email : param.email}"
                                       maxlength="100" required>
                            </div>
                        </div>
                    </section>

                    <!-- dados 2 - endereco -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-map-pin"></i> Endereço</h2>
                        <div class="form-grid">
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="cep">CEP</label>
                                <input type="text" id="cep" name="cep" placeholder="00000-000" 
                                       value="${endereco != null ? endereco.cep : param.cep}"
                                       minlength="9" maxlength="9" pattern="\d{5}-\d{3}" title="Digite um CEP válido no formato 00000-000" required>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="logradouro">Logradouro (Rua, Av)</label>
                                <input type="text" id="logradouro" name="logradouro" 
                                       value="${endereco != null ? endereco.logradouro : param.logradouro}"
                                       minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="numero">Número</label>
                                <input type="text" id="numero" name="numero" placeholder="S/N" 
                                       value="${endereco != null ? endereco.numero : param.numero}"
                                       maxlength="10" required>
                            </div>
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="complemento">Complemento <span class="opcional">(opcional)</span></label>
                                <input type="text" id="complemento" name="complemento" placeholder="Apto, Sala, Galpão" 
                                       value="${endereco != null ? endereco.complemento : param.complemento}"
                                       maxlength="50">
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="bairro">Bairro</label>
                                <input type="text" id="bairro" name="bairro" 
                                       value="${endereco != null ? endereco.bairro : param.bairro}"
                                       minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="cidade">Cidade</label>
                                <input type="text" id="cidade" name="cidade" 
                                       value="${endereco != null ? endereco.cidade : param.cidade}"
                                       minlength="2" maxlength="50" required>
                            </div>
                            <div class="campo span-1">
                                <label class="campo-rotulo" for="uf">UF</label>
                                
                                <!-- Variável para saber qual UF marcar no select -->
                                <c:set var="ufSelecionada" value="${endereco != null ? endereco.uf : param.uf}" />
                                
                                <select id="uf" name="uf" required>
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
                        <!-- se for update, mostra o botao de excluir -->
                        <c:if test="${not empty cliente}">
                            <button type="submit" name="acaoForm" value="excluir" class="btn-secundario" 
                                    style="color: #ef4444; border-color: #fca5a5; margin-right: auto; cursor: pointer;" 
                                    onclick="return confirm('ATENÇÃO: Tem certeza que deseja excluir este cliente permanentemente?');">
                                <i class="ph ph-trash"></i> Excluir Cliente
                            </button>
                        </c:if>

                        <a href="${pageContext.request.contextPath}/clientes" class="btn-secundario">Cancelar</a>
                        
                        <!-- Botão principal envia acaoForm=salvar -->
                        <button type="submit" name="acaoForm" value="salvar" class="btn-principal">
                            <i class="${not empty cliente ? 'ph ph-floppy-disk' : 'ph ph-check'}"></i> 
                            ${not empty cliente ? 'Atualizar Cliente' : 'Salvar Cliente'}
                        </button>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <!-- Máscaras em tempo real -->
    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>