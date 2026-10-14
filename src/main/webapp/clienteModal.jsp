<%@ page contentType="text/html; charset=UTF-8" %>
<dialog id="modal-cliente" class="modal" aria-labelledby="modal-titulo">

    <form id="form-cliente" class="modal-form" action="${pageContext.request.contextPath}/clientes" method="post">

        <header class="modal-cabecalho">
            <div>
                <h2 id="modal-titulo">Novo cliente</h2>
                <p>Preencha os dados e o endereço do cliente.</p>
            </div>
            <button type="button" class="btn-icone" data-fechar aria-label="Fechar">
                <i class="ph ph-x"></i>
            </button>
        </header>

        <ol class="passos" aria-label="Etapas do cadastro">
            <li class="passo ativo">
                <span class="passo-num">1</span>
                <span class="passo-nome">Dados do cliente</span>
            </li>
            <li class="passo">
                <span class="passo-num">2</span>
                <span class="passo-nome">Endereço</span>
            </li>
        </ol>

        <div class="modal-corpo">

            <!-- CLIENTE  -->
            <fieldset class="etapa">
                <legend class="sr-only">Dados do cliente</legend>

                <div class="form-grid">

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="documento">CPF / CNPJ</label>
                        <input type="text" id="documento" name="documento" inputmode="numeric"
                               placeholder="CPF ou CNPJ" required>
                    </div>

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="telefone">Telefone</label>
                        <input type="tel" id="telefone" name="telefone" inputmode="tel"
                               placeholder="(00) 00000-0000" required>
                    </div>

                    <div class="campo span-6">
                        <label class="campo-rotulo" for="nome" id="rotulo-nome">Nome completo</label>
                        <input type="text" id="nome" name="nome" maxlength="100" required>
                    </div>

                    <div class="campo span-6" id="grupo-fantasia" hidden>
                        <label class="campo-rotulo" for="nomeFantasia">
                            Nome fantasia <span class="opcional">(opcional)</span>
                        </label>
                        <input type="text" id="nomeFantasia" name="nomeFantasia" maxlength="100" disabled>
                    </div>

                    <div class="campo span-6">
                        <label class="campo-rotulo" for="email">E-mail</label>
                        <input type="email" id="email" name="email" maxlength="100"
                               placeholder="nome@exemplo.com" required>
                    </div>

                </div>
            </fieldset>

            <!-- ENDEREÇO -->
            <fieldset class="etapa" hidden>
                <legend class="sr-only">Endereço do cliente</legend>

                <div class="form-grid">

                    <div class="campo span-6" role="radiogroup" aria-labelledby="rotulo-tipo-endereco">
                        <span class="campo-rotulo" id="rotulo-tipo-endereco">Tipo de endereço</span>
                        <div class="opcoes">
                            <label class="opcao">
                                <input type="radio" name="tipoEndereco" value="COLETA" checked>
                                <span>Coleta</span>
                            </label>
                            <label class="opcao">
                                <input type="radio" name="tipoEndereco" value="ENTREGA">
                                <span>Entrega</span>
                            </label>
                        </div>
                    </div>

                    <div class="campo span-2">
                        <label class="campo-rotulo" for="cep">CEP</label>
                        <input type="text" id="cep" name="cep" inputmode="numeric"
                               placeholder="00000-000" required>
                    </div>

                    <div class="campo span-4">
                        <label class="campo-rotulo" for="logradouro">Logradouro</label>
                        <input type="text" id="logradouro" name="logradouro" maxlength="50" required>
                    </div>

                    <div class="campo span-2">
                        <label class="campo-rotulo" for="numero">Número</label>
                        <input type="number" id="numero" name="numero" min="1" required>
                    </div>

                    <div class="campo span-4">
                        <label class="campo-rotulo" for="complemento">
                            Complemento <span class="opcional">(opcional)</span>
                        </label>
                        <input type="text" id="complemento" name="complemento" maxlength="30">
                    </div>

                    <div class="campo span-2">
                        <label class="campo-rotulo" for="bairro">Bairro</label>
                        <input type="text" id="bairro" name="bairro" maxlength="30" required>
                    </div>

                    <div class="campo span-3">
                        <label class="campo-rotulo" for="cidade">Cidade</label>
                        <input type="text" id="cidade" name="cidade" maxlength="30" required>
                    </div>

                    <div class="campo span-1">
                        <label class="campo-rotulo" for="uf">UF</label>
                        <select id="uf" name="uf" required>
                            <option value="" selected disabled>--</option>
                            <option>AC</option><option>AL</option><option>AP</option><option>AM</option>
                            <option>BA</option><option>CE</option><option>DF</option><option>ES</option>
                            <option>GO</option><option>MA</option><option>MT</option><option>MS</option>
                            <option>MG</option><option>PA</option><option>PB</option><option>PR</option>
                            <option>PE</option><option>PI</option><option>RJ</option><option>RN</option>
                            <option>RS</option><option>RO</option><option>RR</option><option>SC</option>
                            <option>SP</option><option>SE</option><option>TO</option>
                        </select>
                    </div>

                </div>
            </fieldset>

        </div>

        <footer class="modal-rodape">
            <button type="button" class="btn-secundario" data-etapa="0" data-fechar>Cancelar</button>
            <button type="button" class="btn-secundario" data-etapa="1" id="btn-voltar">
                <i class="ph ph-arrow-left"></i> Voltar
            </button>

            <button type="button" class="btn-principal" data-etapa="0" id="btn-proximo">
                Próximo <i class="ph ph-arrow-right"></i>
            </button>
            <button type="submit" class="btn-principal" data-etapa="1" hidden>
                <i class="ph ph-check"></i> Salvar cliente
            </button>
        </footer>

    </form>
</dialog>

<script src="${pageContext.request.contextPath}/assets/js/clienteModal.js"></script>


