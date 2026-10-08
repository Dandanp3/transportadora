// restante das informações
        System.out.print("Status atual do pedido: ");
        String status = scanner.nextLine();

        System.out.print("Valor do Frete: R$");
        double valorFrete = scanner.nextDouble();
        scanner.nextLine(); // limpar buffer

        System.out.println("Número de rastreio: ");
        String numRastreio = scanner.nextLine();

        // IMPORTANTE: Pedir no formato exato para a conversão do SQL funcionar
        System.out.println("Data de emissão (YYYY-MM-DD): ");
        String dataEmissao = scanner.nextLine();

        // --- BUSCA DE ENDEREÇOS (Simulada para integrar com seu código atual) ---
        // Você deve buscar a lista de endereços do cliente no banco. 
        // Aqui estou chamando o método que você vai criar no EnderecoDAO: buscarPorClienteId
        List<Endereco> enderecosOrigem = enderecoDAO.buscarPorClienteId(remetente.getId());
        if (enderecosOrigem.isEmpty()) {
            System.out.println("O remetente não possui endereços cadastrados!");
            return;
        }
        // Para simplificar, pegando o primeiro endereço do remetente como origem
        Endereco origem = enderecosOrigem.get(0); 

        List<Endereco> enderecosDestino = enderecoDAO.buscarPorClienteId(destinatario.getId());
        if (enderecosDestino.isEmpty()) {
            System.out.println("O destinatário não possui endereços cadastrados!");
            return;
        }
        // Pegando o primeiro endereço do destinatário como destino
        Endereco destino = enderecosDestino.get(0);

        // 1. Cria a entrega com TODOS os dados coletados (Construtor da sua modelagem)
        Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, numRastreio, status, valorFrete, dataEmissao);

        EntregaDAO entregaDAO = new EntregaDAO();
        int idEntrega = entregaDAO.salvarEntrega(novaEntrega); 

        if (idEntrega != -1) {
            novaEntrega.setId(idEntrega); // Guarda o ID no objeto
            entregas.add(novaEntrega);    // Opcional: mantém na lista de memória
            System.out.println("Cadastro finalizado com sucesso!");
        } else {
            System.out.println("Falha ao salvar a entrega no banco.");
        }