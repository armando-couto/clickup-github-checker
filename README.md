# clickup-github-checker
Esse projeto verifica as tarefas que estão em DONE ou EM PRODUÇÃO que as branchs não foram fechadas e faz o disparo para o Slack.

# Passo do Slack:
🚀 Passo a Passo — Criar Webhook no Slack
1️⃣ Acesse o portal de apps do Slack:
👉 https://api.slack.com/apps

2️⃣ Clique em “Create New App”.
Escolha “From scratch”.

Dê um nome para o app (ex.: ClickupCheckerBot).

Escolha o workspace (empresa/canal) onde ele vai atuar.

3️⃣ No menu lateral, vá em:
“Incoming Webhooks” →
Clique em “Activate Incoming Webhooks” (botão superior).

4️⃣ Clique em “Add New Webhook to Workspace”.
Escolha o canal onde ele enviará mensagens (pode ser público ou privado).

Clique em “Permitir” / “Allow”.

5️⃣ Pronto!
O Slack te fornece uma URL assim:
```ruby
https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
```

Essa URL é o seu Webhook. ⚠️ Ela é secreta, quem tiver pode postar no seu canal.

🔗 Coloque no seu application.properties ou application.yml assim:

✅ Em application.properties:
```properties
slack.webhook.url=https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
```

✅ Ou em application.yml:
```yml
slack:
    webhook:
        url: https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
```

🚨 Atenção:
Se quiser enviar mensagens para outros canais, repita o passo 4 e crie outro webhook.

O mesmo webhook pode postar repetidamente no canal escolhido.

---

🛠️🔗 Passo 1 — Criar o Webhook no Slack:

- Acesse: https://api.slack.com/apps
- Crie um app.
- Vá em Incoming Webhooks, ative.
- Adicione um webhook em um canal (pode ser privado ou público).

Pegue a URL gerada, exemplo:
```ruby
https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
```

