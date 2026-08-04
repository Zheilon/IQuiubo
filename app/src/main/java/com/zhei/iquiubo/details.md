## ¿Qué va en core?

En :core va lo genuinamente compartido por varias features. normalmente se divide en:
* (:core:designsystem) → tu tema de Compose, colores, componentes reutilizables (botones, cards).
* (:core:database) → la base de datos Room compartida.
* (:core:domain o :core:model) → entidades y use cases que
cruzan features (ej. el modelo Resultado si lo usan resultados e historial).
* (:core:common) → utilidades, dispatchers, extensiones.