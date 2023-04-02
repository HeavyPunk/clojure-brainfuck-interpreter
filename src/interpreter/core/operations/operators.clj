(ns interpreter.core.operations.operators)

; context
; :program
; :memory-pointer
; :program-pointer
; :memory
; :func-stack
; :cycle-stack

(def operators {"+" (fn [context]
                      (merge context {:memory (assoc
                                               (get context :memory)
                                               (get context :memory-pointer)
                                               (+
                                                (get (get context :memory) (get context :memory-pointer))
                                                1))
                                      :program-pointer (+ (get context :program-pointer) 1)}))
                "-" (fn [context]
                      (merge context {:memory (assoc
                                               (get context :memory)
                                               (get context :memory-pointer)
                                               (-
                                                (get (get context :memory) (get context :memory-pointer))
                                                1))
                                      :program-pointer (+ (get context :program-pointer) 1)}))
                ">" (fn [context]
                      (merge context {:memory-pointer (+ (get context :memory-pointer) 1)
                                      :program-pointer (+ (get context :program-pointer) 1)}))
                "<" (fn [context]
                      (merge context {:memory-pointer (- (get context :memory-pointer) 1)
                                      :program-pointer (+ (get context :program-pointer) 1)}))
                "." (fn [context]
                      (merge context {:memory (assoc (get context :memory) (get context :memory-pointer) (Integer/parseInt (read-line)))
                                      :program-pointer (+ (get context :program-pointer) 1)}))
                "[" (fn [context]
                      (merge context {:cycle-stack (conj (get context :cycle-stack) [(get context :cycle-stack) 10000]) ;10000 to end of current cycle              
                        ;increase program pointer for get cycle end
                                      }))
                "]" (fn [context]
                      ())})
(def ctx {:program "+++>->++>.>+++"
          :memory-pointer 0
          :program-pointer 0
          :memory [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
          :func-stack []
          :cycle-stack []})

(defn operate [ctx]
  (if
   (not= (get ctx :program-pointer) (count (get ctx :program)))
    (operate
     ((get operators (str (get (get ctx :program) (get ctx :program-pointer)))) ctx))
    ctx))

(def c (get operators (get (get ctx :program) (get ctx :program-pointer))))

(def context (operate ctx))

(println context)

(println (operate ctx))
