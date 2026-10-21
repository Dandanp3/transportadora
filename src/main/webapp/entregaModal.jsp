<%@ page contentType="text/html; charset=UTF-8" %>
<dialog id="modal-entrega" class="modal" aria-labelledby="entrega-titulo"
        data-url-busca="${pageContext.request.contextPath}/clientes/buscar">

    <form id="form-entrega" class="modal-form" action="${pageContext.request.contextPath}/entregas" method="post">

        <!-- header -->
        <header class="modal-cabecalho">
            <div>
                <h2 id="entrega-titulo">Nova entrega</h2>
                <p>Escolha os clientes e informe os detalhes da entrega.</p>
            </div>
            <button type="button" class="btn-icone" data-fechar aria-label="Fechar">
                <i class="ph ph-x"></i>
            </button>
        </header>

        <!-- indica as etapas -->
        <ol class="passos" aria-label="Etapas do cadastro">
            <li class="passo ativo">
                <span class="passo-num">1</span>
                <span class="passo-nome">Remetente e destinatário</span>
            </li>
            <li class="passo">
                <span class="passo-num">2</span>
                <span class="passo-nome">Detalhes</span>
            </li>
        </ol>

        <div class="modal-corpo">

            <!-- etapa 1 -->
            <fieldset class="etapa">
                <legend class="sr-only">Remetente e destinatário</legend>

                <div class="form-grid">

                    <!-- remetente -->
                    <div class="grupo span-6">
                        <h3 class="grupo-titulo">
                            <i class="ph ph-paper-plane-tilt"></i> Remetente
                        </h3>

                        <div class="campo">
                            <label class="campo-rotulo" for="remetente-busca">Cliente remetente</label>

                            <!-- campo de busca com lista -->
                            <div class="combo" id="combo-remetente">
                                <i class="ph ph-magnifying-glass combo-icone"></i>
                                <input type="text" id="remetente-busca" class="combo-input"
                                       role="combobox" aria-autocomplete="list" aria-expanded="false"
                                       aria-controls="remetente-lista" autocomplete="off"
                                       placeholder="Buscar por nome, CPF ou CNPJ" required>

                                       <input type="hidden" name="remetenteId" id="remetente-id">

                                       <ul id="remetente-lista" class="combo-lista" role="listbox">
                                        <c:forEach var="cliente" items="${clientes}" >
                                            <li role="option" data-value="${cliente.id}" data-text="${cliente.nome}">
                                                <strong><c:out value="${cliente.nome}"/></strong>
                                                <small><c:out value="${cliente.documentoFormatado}"/></small>
                                            </li>
                                        </c:forEach>
                                       </ul>

                                <!-- aq vai o post -->
                                
                                <button type="button" class="combo-limpar" aria-label="Limpar remetente" hidden>
                                    <i class="ph ph-x"></i>
                                </button>

                                <ul class="combo-lista" id="remetente-lista" role="listbox"
                                    aria-label="Clientes encontrados" hidden></ul>
                            </div>
                        </div>

                        <div class="campo">
                            <label class="campo-rotulo" for="origem-endereco">Endereço de coleta</label>
                            <select id="origem-endereco" name="enderecoOrigemId" required disabled>
                                <option value="" selected disabled>Selecione o remetente primeiro</option>
                            </select>
                        </div>
                    </div>

                    <!-- destinatario -->
                    <div class="grupo span-6">
                        <h3 class="grupo-titulo">
                            <i class="ph ph-map-pin-line"></i> Destinatário
                        </h3>

                        <div class="campo">
                            <label class="campo-rotulo" for="destinatario-busca">Cliente destinatário</label>

                            <div class="combo" id="combo-destinatario">
                                <i class="ph ph-magnifying-glass combo-icone"></i>
                                <input type="text" id="destinatario-busca" class="combo-input"
                                       role="combobox" aria-autocomplete="list" aria-expanded="false"
                                       aria-controls="destinatario-lista" autocomplete="off"
                                       placeholder="Buscar por nome, CPF ou CNPJ" required>

                                <input type="hidden" name="destinatarioId">

                                <button type="button" class="combo-limpar" aria-label="Limpar destinatário" hidden>
                                    <i class="ph ph-x"></i>
                                </button>

                                <ul class="combo-lista" id="destinatario-lista" role="listbox"
                                    aria-label="Clientes encontrados" hidden></ul>
                            </div>
                        </div>

                        <div class="campo">
                            <label class="campo-rotulo" for="destino-endereco">Endereço de entrega</label>
                            <select id="destino-endereco" name="enderecoDestinoId" required disabled>
                                <option value="" selected disabled>Selecione o destinatário primeiro</option>
                            </select>
                        </div>
                    </div>

                </div>
            </fieldset>

            <!-- etapa 2 -->
            <fieldset class="etapa" hidden>
                <legend class="sr-only">Detalhes da entrega</legend>

                <div class="form-grid">

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="num-rastreio">Nº de rastreio</label>
                        <div class="campo-acao">
                            <input type="text" id="num-rastreio" name="numRastreio" class="entrada-acao"
                                   maxlength="20" placeholder="BR00000000"
                                   pattern="[A-Za-z0-9]{6,20}" title="Use de 6 a 20 letras ou números" required>
                            <button type="button" class="btn-icone" id="btn-gerar-rastreio"
                                    aria-label="Gerar número de rastreio" title="Gerar automaticamente">
                                <i class="ph ph-arrows-clockwise"></i>
                            </button>
                        </div>
                    </div>

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="data-emissao">Data de emissão</label>
                        <input type="date" id="data-emissao" name="dataEmissao" required>
                    </div>

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="status-entrega">Status</label>
                        <select id="status-entrega" name="statusEntrega" required>
                            <option value="PENDENTE" selected>Pendente</option>
                            <option value="EM_TRANSITO">Em trânsito</option>
                            <option value="EM_ROTA">Em rota de entrega</option>
                            <option value="CONCLUIDA">Concluída</option>
                        </select>
                    </div>

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="valor-frete">
                            Valor do frete <span class="opcional">(opcional)</span>
                        </label>
                        <div class="campo-prefixo">
                            <span>R$</span>
                            <input type="number" id="valor-frete" name="valorFrete" class="entrada-prefixo"
                                   min="0" step="0.01" placeholder="0,00">
                        </div>
                    </div>

                </div>
            </fieldset>

        </div>

        <!-- rodape -->
        <footer class="modal-rodape">
            <button type="button" class="btn-secundario" data-etapa="0" data-fechar>Cancelar</button>
            <button type="button" class="btn-secundario" data-etapa="1" id="btn-entrega-voltar">
                <i class="ph ph-arrow-left"></i> Voltar
            </button>

            <button type="button" class="btn-principal" data-etapa="0" id="btn-entrega-proximo">
                Próximo <i class="ph ph-arrow-right"></i>
            </button>
            <button type="submit" class="btn-principal" data-etapa="1" hidden>
                <i class="ph ph-check"></i> Salvar entrega
            </button>
        </footer>

    </form>
</dialog>

<script src="${pageContext.request.contextPath}/assets/js/entregaModal.js"></script>