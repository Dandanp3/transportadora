<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início | Dandan Transportes</title>

    <link rel="stylesheet" href="./assets/css/global.css">
    <link rel="stylesheet" href="./assets/css/home.css">

</head>

<body>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">

            <header class="topo">
                <div class="topo-titulo">
                    <h1>Início</h1>
                    <p>Resumo das entregas</p>
                </div>

                <a href="#" class="btn-principal">
                    <i class="ph ph-plus"></i>
                    Cadastrar entrega
                </a>
            </header>

            <div class="conteudo-principal">

                <!-- Cards de resumo -->
                <section class="boxes" aria-label="Resumo das entregas">

                    <a href="#" class="box box-pendente">
                        <div class="box-icone"><i class="ph ph-clock"></i></div>
                        <div class="box-info">
                            <span class="box-titulo">Pendentes</span>
                            <strong class="box-valor">98</strong>
                        </div>
                    </a>

                    <a href="#" class="box box-transito">
                        <div class="box-icone"><i class="ph ph-package"></i></div>
                        <div class="box-info">
                            <span class="box-titulo">Em trânsito</span>
                            <strong class="box-valor">34</strong>
                        </div>
                    </a>

                    <a href="#" class="box box-rota">
                        <div class="box-icone"><i class="ph ph-truck"></i></div>
                        <div class="box-info">
                            <span class="box-titulo">Em rota de entrega</span>
                            <strong class="box-valor">52</strong>
                        </div>
                    </a>

                    <a href="#" class="box box-concluida">
                        <div class="box-icone"><i class="ph ph-check-circle"></i></div>
                        <div class="box-info">
                            <span class="box-titulo">Concluídas</span>
                            <strong class="box-valor">87</strong>
                        </div>
                    </a>

                </section>

                <!-- Entregas recentes -->
                <section class="recentes">
                    <div class="recentes-cabecalho">
                        <h2>Entregas recentes</h2>
                        <a href="#" class="link-ver-todas">Ver todas</a>
                    </div>

                    <div class="tabela-wrapper">
                        <table class="tabela-recentes">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Cliente</th>
                                    <th>Destino</th>
                                    <th>Data</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>BR03594754</td>
                                    <td>Maria Silva</td>
                                    <td>Belo Horizonte, MG</td>
                                    <td>13/10/2026</td>
                                    <td><span class="status status-transito">Em trânsito</span></td>
                                </tr>
                                <tr>
                                    <td>BR03594801</td>
                                    <td>João Souza</td>
                                    <td>Contagem, MG</td>
                                    <td>13/10/2026</td>
                                    <td><span class="status status-rota">Em rota de entrega</span></td>
                                </tr>
                                <tr>
                                    <td>BR03594622</td>
                                    <td>Comercial Alfa Ltda</td>
                                    <td>Betim, MG</td>
                                    <td>12/10/2026</td>
                                    <td><span class="status status-concluida">Concluída</span></td>
                                </tr>
                                <tr>
                                    <td>BR03594517</td>
                                    <td>Ana Pereira</td>
                                    <td>Uberlândia, MG</td>
                                    <td>12/10/2026</td>
                                    <td><span class="status status-pendente">Pendente</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </section>

            </div>
        </main>
    </div>

</body>
</html>