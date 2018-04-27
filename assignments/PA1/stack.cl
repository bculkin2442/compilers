(*
  *  CS410 Spring 2018
  *
  *  Programming Assignment 1
  *    Implementation of a simple stack machine.
  *
  *)

(*
  * Container classes for storing in the list
  *)
class StackOpr {
	printme(io : IO) : Object {
		0
	};
};

class IntStackOpr inherits StackOpr {
	val : Int;
	
	printme(io : IO) : Object {
		{
			io.out_int(val);
			0;
		}
	};

	make(i : Int) : StackOpr {
		(let itm : IntStackOpr <- new IntStackOpr in {
			itm.sval(i);

			itm;
		})
	};

	sval(i : Int) : Object {
		val <- i
	};
	
	gval() : Int {
		val
	};
};

class PlusStackOpr inherits StackOpr {
	printme(io : IO) : Object {
		{
			io.out_string("+");
			0;
		}
	};
};

class SStackOpr inherits StackOpr {
	printme(io : IO) : Object {
		{
			io.out_string("s");
			0;
		}
	};
};

(* 
  * List container, borrowed from example code.
*)

(* ----------------------- BORROWED CODE
   *)
(*
 *  The List class has 4 operations defined on List objects. If 'l' is
 *  a list, then the methods dispatched on 'l' have the following effects:
 *
 *    isNil() : Bool		Returns true if 'l' is empty, false otherwise.
 *    head()  : StackOpr	Returns the operation at the head of 'l'.
 *				If 'l' is empty, execution aborts.
 *    tail()  : List		Returns the remainder of the 'l',
 *				i.e. without the first element.
 *    cons(i : StackOpr) : List	Return a new list containing i as the
 *				first element, followed by the
 *				elements in 'l'.
 *
 *  There are 2 kinds of lists, the empty list and a non-empty
 *  list. We can think of the non-empty list as a specialization of
 *  the empty list.
 *  The class List defines the operations on empty list. The class
 *  Cons inherits from List and redefines things to handle non-empty
 *  lists.
 *)


class List {
   -- Define operations on empty lists.

   isNil() : Bool { true };

   -- Since abort() has return type Object and head() has return type
   -- Int, we need to have an Int as the result of the method body,
   -- even though abort() never returns.

   head()  : StackOpr { { abort(); new StackOpr; } };

   -- As for head(), the self is just to make sure the return type of
   -- tail() is correct.

   tail()  : List { { abort(); self; } };

   -- When we cons and element onto the empty list we get a non-empty
   -- list. The (new Cons) expression creates a new list cell of class
   -- Cons, which is initialized by a dispatch to init().
   -- The result of init() is an element of class Cons, but it
   -- conforms to the return type List, because Cons is a subclass of
   -- List.

   cons(i : StackOpr) : List {
      (new Cons).init(i, self)
   };

};


(*
 *  Cons inherits all operations from List. We can reuse only the cons
 *  method though, because adding an element to the front of an emtpy
 *  list is the same as adding it to the front of a non empty
 *  list. All other methods have to be redefined, since the behaviour
 *  for them is different from the empty list.
 *
 *  Cons needs two attributes to hold the integer of this list
 *  cell and to hold the rest of the list.
 *
 *  The init() method is used by the cons() method to initialize the
 *  cell.
 *)

class Cons inherits List {

   car : StackOpr;	-- The element in this list cell

   cdr : List;	-- The rest of the list

   isNil() : Bool { false };

   head()  : StackOpr { car };

   tail()  : List { cdr };

   init(i : StackOpr, rest : List) : List {
      {
	 car <- i;
	 cdr <- rest;
	 self;
      }
   };

};

(* -------------------- END BORROWED CODE
   *)

class Main inherits IO {
	stk : List;

	print_list(l : List) : Object {
		if l.isNil() then out_string("\n")
		else {
			l.head().printme(self);
			out_string(" ");
			print_list(l.tail());
		}
		fi
	};

	do_add() : Object {
		case stk.head() of
			lhs : IntStackOpr => {
				stk <- stk.tail();
				case stk.head() of
					rhs : IntStackOpr => 
						(let nval : Int <- lhs.gval() + rhs.gval() in
							(let itm : StackOpr <- lhs.make(nval) in
							  stk <- stk.tail().cons(itm)
							  ));
					defa : Object      => { abort(); 0; };
				esac;
			};
			def : Object      => { abort(); 0;};
		esac
	};

	do_swap() : Object {
		(let lhs : StackOpr <- stk.head() in {
			stk <- stk.tail();
			(let rhs : StackOpr <- stk.head() in {
				stk <- stk.tail().cons(lhs).cons(rhs);
			  });
		  })
	};

	stack_rest(s : String) : Object {
		if s = "e" then
			(let hd : StackOpr <- stk.head() in {
			  	stk <- stk.tail();

				case hd of
					add  : PlusStackOpr => do_add();
					swap : SStackOpr    => do_swap();
					def  : Object       => 0;
				esac;
			})
		else
			if s = "d" then
				print_list(stk)
			else 
				(let itm : IntStackOpr <- new IntStackOpr in {
					itm.sval(new A2I.a2i(s));

					stk <- stk.cons(itm);
				})
			fi
		fi
	};

	main() : Object {
	{
		stk <- new List;

		while true loop {
			out_string(">");
			(let s : String <- in_string() in
			  if s = "x" then
			  	abort()
			  else
			  	if s = "+" then
					stk <- stk.cons(new PlusStackOpr)
				else
					if s = "s" then
						stk <- stk.cons(new SStackOpr)
					else
						stack_rest(s)
					fi
				fi
			  fi
			  );
			0;
		}
		pool;
	}
	};
};
